package cn.itcast.ssm.controller.member;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.ssm.mapper.member.MemberMapper;
import cn.itcast.ssm.model.member.Member;
import cn.itcast.ssm.service.member.MemberService;

/**
 * 负责一些公共页面的跳转
 * @author Administrator
 *
 */
@Controller
public class FirstAction {
	
	@Autowired
	private MemberService memberService;
	/**
	 * 
	 * @description:跳转到系统首页
	 * @author ：zc
	 * @date ：2017-2-7 上午8:24:12 
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/first")
	public String first(HttpServletRequest request, HttpSession session)throws Exception{
		if (session.getAttribute("member") == null) {
		    return "/login";
		}
		return "/first";
	}
	
	/**
	 * @description: 跳转到系统 的主界面
	 * @author ：zc
	 * @date ：2017-2-10 下午1:08:54 
	 * @param session
	 * @return
	 */
	@RequestMapping("/main")
	public String main(HttpSession session) {
	    if (session.getAttribute("member") == null) {
	        return "/login";
	    }
	    //判断在线用户的密码是否被修改了如果被修改了，直接返回登录页面
	    Member sessionMember = (Member) session.getAttribute("member");
	    //通过id，获取修改之后的密码信息（可以解决这个问题：如果管理员修改自己的密码，怎样处理呢？）
	    Member newMember = memberService.findMemberById(sessionMember.getId().toString());
	    if (!sessionMember.getLoginpwd().equals(newMember.getLoginpwd())) {
	    	session.invalidate();
	    	return "/login";
	    }
	    return "/main";
	}
	
	/**
	 * 
	 * @description: 跳转到登录成功之后的欢迎页面
	 * @author ：zc
	 * @date ：2017-2-7 上午8:24:49 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/welcome")
	public String welcome(HttpServletRequest request)throws Exception{
		return "/login";
		
	}
	
	/**
	 * 
	 * @description: 跳转到系统错误展示页面
	 * @author ：zc
	 * @date ：2017-2-7 上午8:28:03 
	 * @param model
	 * @return
	 */
	@RequestMapping("/error")
	public String error (Model model) {
	    return "/error";
	}
	
	/**
     * 
     * @description: 跳转到会员注册页面
     * @author ：zc
     * @date ：2017-2-7 上午8:28:03 
     * @return
     */
    @RequestMapping("/toRegister")
    public String error () {
        return "/register";
    }
	
    
    /**
     * 
     * @description: 跳转到会员注册成功的提示页面
     * @author ：zc
     * @date ：2017-2-7 上午8:28:03 
     * @return
     */
    @RequestMapping("/success")
    public String success () {
        return "/success";
    }
    
    /**
     * 
     * @description:跳转到使用帮助的页面
     * @author ：zc
     * @date ：2017-2-7 上午10:28:40 
     * @return
     */
    @RequestMapping("/help")
    public String help() {
        return "forward:WEB-INF/help/help.html";
    }
    
    
    /**
     * @description: 跳转到main_center.jsp 页面
     * @author ：zc
     * @date ：2017-2-10 下午6:45:49 
     * @return
     */
    @RequestMapping("/member")
    public String mainCenter() {
        return "/member/member";
    }
    
    /**
     * @description: 跳转到 会员图表页面
     * @author ：zc
     * @date ：2017-3-25 20:51:26
     * @return
     */
    @RequestMapping("/memberChart")
    public String memberChart() {
        return "/member/memberChart";
    }
    
    /**
     * @description: 跳转到 会员卡充值记录页面
     * @author ：zc
     * @date ：2017-2-13 上午11:59:11 
     * @return
     */
    @RequestMapping("/cardRechargeRecord")
    public String cardRechargeRecord() {
        return "/cardRechargeRecord/cardRechargeRecord";
    }
    
    /**
     * @description: 跳转到 会员卡充值图表页面
     * @author ：zc
     * @date ：2017-3-25 20:51:26
     * @return
     */
    @RequestMapping("/cardRechargeRecordChart")
    public String cardRechargeRecordChart() {
        return "/cardRechargeRecord/cardRechargeRecordChart";
    }
    
    
    /**
     * @description: 跳转到会员卡信息页面
     * @author ：zc
     * @date ：2017-2-13 上午11:59:11 
     * @return
     */
    @RequestMapping("/memberCard")
    public String memberCard() {
        return "/memberCard/memberCard";
    }
    
    
    
    /**
     * @description: 跳转到会员卡图表页面
     * @author ：zc
     * @date ：2017-2-13 上午11:59:11 
     * @return
     */
    @RequestMapping("/memberCardChart")
    public String memberCardChart() {
        return "/memberCard/memberCardChart";
    }
    
    /**
     * @description: 会员卡挂失/激活记录页面
     * @author ：zc
     * @date ：2017-2-13 上午11:59:11 
     * @return
     */
    @RequestMapping("/cardRecord")
    public String cardRecord() {
        return "/cardRecord/cardRecord";
    }
    
    /**
     * @description: 跳转到会员卡积分调整记录页面
     * @author ：zc
     * @date ：2017-2-13 上午11:59:11 
     * @return
     */
    @RequestMapping("/pointRecord")
    public String pointRecord() {
        return "/pointRecord/pointRecord";
    }
    
    /**
     * 跳转到积分图表页面
     * @author ：zc
     * @date ：2017年3月24日 下午7:17:00 
     * @return
     */
    @RequestMapping("/pointRecordChart")
    public String pointRecordChart() {
    	return "/pointRecord/pointRecordChart";
    }
    
    /**
     * @description: 跳转到消费记录页面
     * @author ：zc
     * @date ：2017-2-15 上午11:58:00 
     * @return
     */
    @RequestMapping("/consumeRecord")
    public String consumeRecord() {
        return "/consumeRecord/consumeRecord";
    }
    
    /**
     * 跳转到销售图表页面
     * @author ：zc
     * @date ：2017年3月23日 下午8:01:49 
     * @return
     */
    @RequestMapping("/consumeRecordChart")
    public String consumeRecordChart() {
    	return "/consumeRecord/consumeRecordChart";
    }
    

    
}	
