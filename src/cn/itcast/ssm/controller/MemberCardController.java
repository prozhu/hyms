package cn.itcast.ssm.controller;

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

import cn.itcast.ssm.exception.CustomException;
import cn.itcast.ssm.model.Cardrechargerecord;
import cn.itcast.ssm.model.Member;
import cn.itcast.ssm.model.Membercard;
import cn.itcast.ssm.service.MemberCardService;
import cn.itcast.ssm.service.MemberService;
import cn.itcast.ssm.util.JSONUtil;
import cn.itcast.ssm.util.excel.DjExcelCreator;
import cn.itcast.ssm.util.excel.DjExcelDataRender;

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
    @RequestMapping("/addMemberCard")
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
    @RequestMapping("/memberCardActivate")
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
    @RequestMapping("/updateMemberCardInfo")
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
    
}
