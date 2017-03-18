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

import cn.itcast.ssm.model.Cardrecord;
import cn.itcast.ssm.model.Member;
import cn.itcast.ssm.service.CardRecordService;
import cn.itcast.ssm.util.JSONUtil;
import cn.itcast.ssm.util.excel.DjExcelCreator;

/**
 * 会员卡挂失、激活记录的Controller
 * @author Administrator
 *
 */
@RequestMapping("/cardRecord")
@Controller
public class CardRecordController extends BaseController{

    @Autowired
    private CardRecordService cardRecordService;
    
    /**
     * @description:  查询所有的会员卡激活或者挂失信息
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
        List<Cardrecord> cardRecordList = new ArrayList<Cardrecord>();
        if ("0".equals(member.getMembertype())) {
            //管理员：查询所有信息
            total = cardRecordService.getCount(startTime, endTime, keyword, null);
            cardRecordList = cardRecordService.findCardRecordByCondition(null, pageNow, pageSize, startTime, endTime, keyword,  sort, order);
        } else {
            //普通会员：查询个人信息
        	total = cardRecordService.getCount(startTime, endTime, keyword, member.getMemberid().toString());
            cardRecordList = cardRecordService.findCardRecordByCondition(member.getMemberid().toString(),pageNow, pageSize, startTime, endTime, keyword,  sort, order);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", cardRecordList == null ? 0 : cardRecordList);
        return writeAjaxResponse(JSONUtil.getJson(result), response);
    }
    
    /**
     * @description: 导出会员卡的激活或者挂失记录
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
        List<Cardrecord> cardRecordList = new ArrayList<Cardrecord>();
        if ("0".equals(member.getMembertype())) {
            //管理员：查询所有信息
            cardRecordList = cardRecordService.findCardRecordByCondition(null, null, null, startTime, endTime, keyword,  null, null);
        } else {
            //普通会员：查询个人信息
            cardRecordList = cardRecordService.findCardRecordByCondition(member.getMemberid().toString(),null, null, startTime, endTime, keyword,  null, null);
        }
        String colNames[] = { "会员名称", "会员卡号", "操作类型", "消费时间"};
        String colKeys[] = { "membername", "membercardid", "operationtype", "changetime"};
        DjExcelCreator creator = new DjExcelCreator(colNames, colKeys, "会员卡激活/挂失记录表");
        creator.setColumnsWidth(new Integer[]{ 10,  20, 10, 20});
        creator.createForWeb(cardRecordList, response);
        return null;
    }
}
