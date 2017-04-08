package cn.prozhu.ssm.controller.membercard;

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

import cn.prozhu.ssm.controller.base.BaseController;
import cn.prozhu.ssm.exception.CustomException;
import cn.prozhu.ssm.model.member.Member;
import cn.prozhu.ssm.model.membercard.Membercard;
import cn.prozhu.ssm.service.member.MemberService;
import cn.prozhu.ssm.service.membercard.MemberCardService;
import cn.prozhu.ssm.util.JSONUtil;
import cn.prozhu.ssm.util.StringUitl;
import cn.prozhu.ssm.util.excel.DjExcelCreator;
import cn.prozhu.ssm.util.excel.DjExcelDataRender;

/*
 * 会员卡的Controller
 */
@RequestMapping("/memberCard")
@Controller
public class MemberCardController extends BaseController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberCardService memberCardService;
    
    /**
     * @description : 添加会员卡
     * @author ：zc 
     * @date ：2017-2-14 下午2:26:25 
     * @param response
     * @param ids
     * @return
     */
    @RequestMapping(value = "/addMemberCard", method = {RequestMethod.POST})
    public String addMemberCard(HttpServletResponse response, String ids) {
        try {
            memberCardService.saveMemberCard(ids, memberService);
        } catch (Exception e) {
            return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""), response);
        }
        return writeAjaxResponse(JSONUtil.result(true, "", "", ""), response);
    }
    
  /**
   * @description: 会员卡的激活、 挂失操作
   * @author ：zc
   * @date ：2017-2-15 下午3:45:55 
   * @param ids
   * @param response
   * @return
   */
    @RequestMapping(value = "/memberCardActivate", method = {RequestMethod.POST})
    public String memberCardActivate(String ids, String flag,  HttpServletResponse response) {
        try {
            memberCardService.updateMemberCard(ids, flag);
        } catch (Exception e) {
            return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""), response);
        }
        return writeAjaxResponse(JSONUtil.result(true, "", "", ""), response);
    }
    
    /**
     * @description: 查询所有会员卡信息(可以根据条件查询)
     * @author ：zc
     * @date ：2017-2-14 下午2:26:50 
     * @param startTime 
     * @param endTime
     * @param keyword
     * @param response
     * @param request
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
        List<Membercard> memberCardList = new ArrayList<Membercard>();
        if ("0".equals(member.getMembertype())) {
            //管理员：查询所有信息
            total = memberCardService.getCount(startTime, endTime, keyword);
            memberCardList = memberCardService.findMemberCardByCondition(pageNow, pageSize, startTime, endTime, keyword,  sort, order, memberService);
        } else {
            //普通会员：查询个人信息
            memberCardList= memberCardService.findMemberCardByMemberId(member.getMemberid().toString());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", memberCardList == null ? 0 : memberCardList);
        return writeAjaxResponse(JSONUtil.getJson(result), response);
    }
    
    /**
     * @description: 导出会员卡信息
     * @author ：zc
     * @date ：2017-2-15 上午10:34:00 
     * @param startTime
     * @param endTime
     * @param keyword
     * @param session
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportExcel")
    public String exportMemberInfoExcel(String startTime, String endTime, String keyword, HttpSession session, HttpServletResponse response) throws Exception {
      //获取session中的会员信息
        Member member = (Member) session.getAttribute("member");
        List<Membercard> memberCardList = new ArrayList<Membercard>();
        if ("0".equals(member.getMembertype())) {
            //管理员：查询所有信息
            memberCardList = memberCardService.findMemberCardByCondition(null, null, startTime, endTime, keyword,  null, null, memberService);
        } else {
            //普通会员：查询个人信息
            memberCardList = memberCardService.findMemberCardByMemberId(member.getMemberid().toString());
        }
        String colNames[] = { "会员名称", "会员卡号", "卡状态", "会员卡级别", "会员折扣", "卡余额", "累计充值", "累计消费", "累计积分", "开卡日期"};
        String colKeys[] = { "membername", "membercardid", "cardstatus", "cardgrade", "discount", "balance", "totalrecharge", "totalconsumption", "totalpoint", "opendate"};
        DjExcelCreator creator = new DjExcelCreator(colNames, colKeys, "会员卡基本信息表");
        creator.setColumnsWidth(new Integer[]{ 20, 20, 10, 10,  10, 20, 20, 20,  20,  40});
        creator.addNumberCol("totalconsumption", "0.00");
        creator.addNumberCol("balance", "0.00");
        creator.addSumColumn(new String[]{"balance", "totalrecharge", "totalconsumption", "totalpoint"});
        creator.addRenderColumn("cardstatus", new DjExcelDataRender() {
            public String render(Object obj) {
                return  obj == null?"": obj.toString().equals("0")?"正常":obj.toString().equals("1")?"禁用":"挂失";
            }
        });
        creator.createForWeb(memberCardList, response);
        return null;
    }
    
    /**
     * @description: 修改会员卡信息
     * @author ：zc
     * @date ：2017-2-14 下午6:14:03 
     * @return
     */
    @RequestMapping(value = "/updateMemberCardInfo", method = {RequestMethod.POST})
    public String updateMemberCardInfo(String recharge, String consume, String id,  String point, HttpServletResponse response) {
        Integer state = 0;
        try {
            state = memberCardService.updateMemberCard(recharge, consume, id, point);
        } catch (CustomException e) {
            return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""), response);
        }
        if (state > 0 && state != null) {
            return writeAjaxResponse(JSONUtil.result(true,"", "", ""), response);
        } else {
            return writeAjaxResponse(JSONUtil.result(false, "充值或消费失败！", "", ""), response);
        }
    }
    
    
    /**
     * 会员卡记录图表()
     * @author ：zc
     * @date ：2017年3月24日 上午9:35:29 
     * @param response
     * @param mark ：年度、季度、月度显示图表(year, quarter, month)
     * @param markYear ：年份(2017)
     * @param time : 指定时间
     * @return
     */
    @RequestMapping(value = "/memberCardChart", method = {RequestMethod.GET})
    public String memberCardChart(HttpServletResponse response, HttpSession session, String mark, String markYear, String time) {
        //获取session中的会员信息
        Member member = (Member) session.getAttribute("member");
        if (!"0".equals(member.getMembertype())) {
        	return writeAjaxResponse(JSONUtil.result(false, "无权访问!", "", ""), response);
        }
    	List<Map<String, Object>> con = memberCardService.memberCardChart(mark, markYear, time);
    	if (StringUitl.isNullOrEmpty(con)) {
    		return writeAjaxResponse(JSONUtil.result(false, "没有数据", "", JSONUtil.getJson(con)), response);
    	}
    	return writeAjaxResponse(JSONUtil.result(true, "", "", JSONUtil.getJson(con)), response);
    }
    
    /**
     * 获取会员卡表中的所有年份
     * @author ：zc
     * @date ：2017年3月24日 下午7:25:58 
     * @param response
     * @return
     */
    @RequestMapping(value = "/findMemberCardYears", method = {RequestMethod.GET})
    public String findMemberCardYears(HttpServletResponse response) {
    	List<String> list = memberCardService.findMemberCardYears();
    	if (StringUitl.isNullOrEmpty(list)) {
    		return writeAjaxResponse(JSONUtil.result(false, "没有数据", "", JSONUtil.getJson(list)), response);
    	}
    	return writeAjaxResponse(JSONUtil.result(true, "", "", JSONUtil.getJson(list)), response);
    }
    
    
    
}
