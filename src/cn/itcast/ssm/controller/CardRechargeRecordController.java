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

import cn.itcast.ssm.model.Cardrechargerecord;
import cn.itcast.ssm.model.Member;
import cn.itcast.ssm.service.CardRechargeRecordService;
import cn.itcast.ssm.util.JSONUtil;
import cn.itcast.ssm.util.excel.DjExcelCreator;

/**
 * 充值记录的Controller
 * @author Administrator
 */
@RequestMapping("/cardRechargeRecord")
@Controller
public class CardRechargeRecordController extends BaseController{

    @Autowired
    private CardRechargeRecordService cardRechargeRecordService;
    
    /**
     * @description:  查询所有的充值记录信息
     * @author ：zc
     * @date ：2017-2-15 上午9:25:57 
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
        List<Cardrechargerecord> cardRechargeRecordList = new ArrayList<Cardrechargerecord>();
        if ("0".equals(member.getMembertype())) {
            //管理员：查询所有信息
            total = cardRechargeRecordService.getCount(startTime, endTime, keyword, null);
            cardRechargeRecordList = cardRechargeRecordService.findCardRechargeRecordByCondition(pageNow, pageSize, startTime, endTime, keyword,  sort, order);
        } else {
            //普通会员：查询个人信息
        	total = cardRechargeRecordService.getCount(startTime, endTime, keyword, member.getMemberid().toString());
            cardRechargeRecordList = cardRechargeRecordService.findCardRechargeRecordByMemberIdAndCondition(member.getMemberid().toString(), pageNow, pageSize, startTime, endTime, keyword,  sort, order);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", cardRechargeRecordList == null ? 0 : cardRechargeRecordList);
        return writeAjaxResponse(JSONUtil.getJson(result), response);
    }
    
    /**
     * @description: 导出会员卡的充值记录
     * @author ：zc
     * @date ：2017-2-15 上午10:25:45 
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
        List<Cardrechargerecord> cardRechargeRecordList = new ArrayList<Cardrechargerecord>();
        if ("0".equals(member.getMembertype())) {
            //管理员：查询所有信息
            cardRechargeRecordList = cardRechargeRecordService.findCardRechargeRecordByCondition(null, null, startTime, endTime, keyword, null, null);
        } else {
            //普通会员：查询个人信息
            cardRechargeRecordList = cardRechargeRecordService.findCardRechargeRecordByMemberIdAndCondition(member.getMemberid().toString(), null, null, startTime, endTime, keyword,  null, null);
        }
        String colNames[] = { "会员名称", "会员卡号", "充值金额", "充值时间"};
        String colKeys[] = { "membername", "membercardid", "rechargemoney", "changetime"};
        DjExcelCreator creator = new DjExcelCreator(colNames, colKeys, "会员卡充值记录");
        creator.setColumnsWidth(new Integer[]{ 20, 30, 20, 40});
        creator.addNumberCol("rechargemoney", "0.00");
        creator.addSumColumn("rechargemoney");
        creator.createForWeb(cardRechargeRecordList, response);
        return null;
    }
    
}
