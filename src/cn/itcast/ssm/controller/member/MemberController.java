package cn.itcast.ssm.controller.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.itcast.ssm.controller.base.BaseController;
import cn.itcast.ssm.exception.CustomException;
import cn.itcast.ssm.model.member.Member;
import cn.itcast.ssm.service.member.MemberService;
import cn.itcast.ssm.service.system.SysService;
import cn.itcast.ssm.util.JSONUtil;
import cn.itcast.ssm.util.StringUitl;
import cn.itcast.ssm.util.excel.DjExcelCreator;
import cn.itcast.ssm.util.excel.DjExcelDataRender;

/**
 *会员Controller
 */
@RequestMapping("/member")
@Controller
public class MemberController extends BaseController{
	@Autowired
	private MemberService memberService;
	
	@Autowired
    private SysService sysService;

	
	/**
	 * @description:用户注册
	 * @author ：zc
	 * @date ：2017-2-6 下午5:22:30 
	 * @return
	 */
	@RequestMapping(value = "/register", method = {RequestMethod.POST})
	public String  register(Member member, HttpServletResponse response, HttpSession session) {
	    //获取session中的用户信息
	    Member oldMember = (Member) session.getAttribute("member");
	    Integer state = 0;
	    //id 不存在，就保存
	    if (member.getId() == null || member.getId().equals("")) {
	        state = memberService.save(member);
	    } else {//否则为更新
	        state = memberService.updateMember(member);
	    }
	    //通过id，获取修改之后的密码信息（可以解决这个问题：如果管理员修改自己的密码，怎样处理呢？）
	    Member newMember = memberService.findMemberById(oldMember.getId().toString());
	    //表示密码被修改了
	    if (state == 2) {
	        if (oldMember != null && "0".equals(oldMember.getMembertype()) && newMember.getLoginpwd().equals(oldMember.getLoginpwd()) ) {
	            //如果是管理员修改别人的密码，就不用重新登录 ，如果管理员修改自己的密码，怎样处理呢？
	            return writeAjaxResponse(JSONUtil.result(true, "修改成功！", "1", ""), response);
	        } else {
	            //干掉session
	            session.invalidate();
	            return writeAjaxResponse(JSONUtil.result(true, "密码被修改了，请重新登录系统！", "2", ""), response);
	        }
	    }
	    if (state > 0 && state != null && state != 2) {
	        return writeAjaxResponse(JSONUtil.result(true, "修改成功！", "1", ""), response);
	    } else {
	        return writeAjaxResponse(JSONUtil.result(false, "修改失败！", "0", ""), response);
	    }
	}

	/**
	 * 
	 * @description: 校验登录用户名是否存在
	 * @author ：zc
	 * @date ：2017-2-7 下午2:25:53 
	 * @return 1表示：不存在    0：表示存在
	 */
	@RequestMapping(value = "/checkLoginName", method = {RequestMethod.POST})
	public String isCheckLoginName(String loginname, HttpServletResponse response) throws Exception {
	    if (StringUitl.isNullOrEmpty(loginname)) {
	        return writeAjaxResponse(JSONUtil.getJson(false), response);
	    }
	    Member member = sysService.findMemberByLoginName(loginname);
	    if (member != null) {
	        return writeAjaxResponse(JSONUtil.getJson(false), response);
	    }
	    return writeAjaxResponse(JSONUtil.getJson(true), response);
	}
	
	
	/**
	 * @description: 删除会员信息
	 * @author ：zc
	 * @date ：2017-2-7 下午5:03:34 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delMember", method = {RequestMethod.POST})
	public String delMember(HttpServletRequest request, HttpServletResponse response, String ids) {
	    Integer num = null;
        try {
            num = memberService.deleteMember(ids);
        } catch (CustomException e) {
            //删除管理员则，抛出异常
            return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""), response);
        }
	    if (num > 0 && num != null) {
	        return writeAjaxResponse(JSONUtil.result(true, "", "", ""), response);
	    }
	    return writeAjaxResponse(JSONUtil.result(false, "", "", ""), response);
	}
	
	/**
	 * @description: 导出会员信息报表
	 * @author ：zc
	 * @date ：2017-2-12 17:08:18 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/exportMemberInfoExcel", method = {RequestMethod.POST})
	public String exportMemberInfoExcel(String startTime, String endTime, String keyword, HttpSession session, HttpServletResponse response) throws Exception {
	  //获取session中的会员信息
        Member member = (Member) session.getAttribute("member");
		//获取用户信息
		List<Member> list = new ArrayList<Member>();
		if ("0".equals(member.getMembertype())) {
            //管理员：查询所有信息
            list = memberService.findMemberByCondition(null, null, startTime, endTime, keyword, null, null);
        } else {
            //普通会员：查询个人信息
            list.add(memberService.findMemberById(member.getId().toString()));
        }
		String colNames[] = { "会员名称", "登录名称", "登录密码", "性别", "年龄", "电话号码", "会员类型", "注册时间"};
		String colKeys[] = { "membername", "loginname", "loginpwd", "sex", "age", "phone", "membertype", "registertime"};
		DjExcelCreator creator = new DjExcelCreator(colNames, colKeys, "会员信息");
		creator.setColumnsWidth(new Integer[]{ 20, 20, 40, 10, 10, 20, 15, 40});
    	creator.addRenderColumn("membertype", new DjExcelDataRender() {
                public String render(Object obj) {
                    return  obj == null?"": obj.toString().equals("1")?"会员":"管理员";
                }
         });
		creator.createForWeb(list, response);
		return null;
	}
	
	
	/**
	 * @description: 通过条件查询会员信息
	 * @author ：zc
	 * @date ：2017-2-13 下午4:05:10 
	 * @return
	 */
	@RequestMapping("/findAllByCondition")
	public String findAllByCondition(String startTime, String endTime, String keyword, HttpServletResponse response, HttpServletRequest request) {
	    //排序的列名
	    String sort = request.getParameter("sort");
	    //升序/降序
	    String order = request.getParameter("order");
	    String pageNow = request.getParameter("page");
        String pageSize = request.getParameter("rows");
        Integer total = 1;
      //获取session中的会员信息
        Member member = (Member) request.getSession().getAttribute("member");
        List<Member> memberList = new ArrayList<Member>();
        if ("0".equals(member.getMembertype())) {
            //管理员：查询所有信息
            total = memberService.getCount(startTime, endTime, keyword);
            memberList = memberService.findMemberByCondition(pageNow, pageSize, startTime, endTime, keyword, sort, order);
        } else {
            //普通会员：查询个人信息
        	total = memberService.getCount(startTime, endTime, keyword);
            memberList.add(memberService.findMemberById(member.getId().toString()));
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", memberList == null ? 0 : memberList);
        return writeAjaxResponse(JSONUtil.getJson(result), response);
	}
	
	/**
     * 会员记录图表
     * @author ：zc
     * @date ：2017年3月24日 上午9:35:29 
     * @param response
     * @param mark ：年度、季度、月度显示图表(year, quarter, month)
     * @param markYear ：年份(2017)
     * @param time : 指定时间
     * @return
     */
    @RequestMapping(value = "/memberChart", method = {RequestMethod.GET})
    public String memberChart(HttpServletResponse response, String mark, String markYear, String time) {
    	List<Map<String, Object>> con = memberService.memberChart(mark, markYear, time);
    	if (StringUitl.isNullOrEmpty(con)) {
    		return writeAjaxResponse(JSONUtil.result(false, "没有数据", "", JSONUtil.getJson(con)), response);
    	}
    	return writeAjaxResponse(JSONUtil.result(true, "", "", JSONUtil.getJson(con)), response);
    }
    
    /**
     * 获取会员表中的所有年份
     * @author ：zc
     * @date ：2017年3月24日 下午7:25:58 
     * @param response
     * @return
     */
    @RequestMapping(value = "/findMemberYears", method = {RequestMethod.GET})
    public String findMemberYears(HttpServletResponse response) {
    	List<String> list = memberService.findMemberYears();
    	if (StringUitl.isNullOrEmpty(list)) {
    		return writeAjaxResponse(JSONUtil.result(false, "没有数据", "", JSONUtil.getJson(list)), response);
    	}
    	return writeAjaxResponse(JSONUtil.result(true, "", "", JSONUtil.getJson(list)), response);
    }
    
    
    /**
     * 按照年龄查询会员报表
     * @author ：zc
     * @date ：2017年3月24日 下午7:25:58 
     * @param response
     * @return
     */
    @RequestMapping(value = "/memberChartByAge", method = {RequestMethod.GET})
    public String memberChartByAge(HttpServletResponse response) {
    	List<Map<String, Object>> list = memberService.memberChartByAge();
    	if (StringUitl.isNullOrEmpty(list)) {
    		return writeAjaxResponse(JSONUtil.result(false, "没有数据", "", JSONUtil.getJson(list)), response);
    	}
    	return writeAjaxResponse(JSONUtil.result(true, "", "", JSONUtil.getJson(list)), response);
    }

	
}
