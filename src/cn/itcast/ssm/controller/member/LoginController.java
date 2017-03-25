package cn.itcast.ssm.controller.member;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.ssm.controller.base.BaseController;
import cn.itcast.ssm.model.member.Member;
import cn.itcast.ssm.service.system.SysService;
import cn.itcast.ssm.util.JSONUtil;
import cn.itcast.ssm.util.MailSenderUtil;

/**
 *用户登录的Controller
 */
@Controller
public class LoginController extends BaseController{
    
    //定义全局变量，用来统计在线人数
    public static Integer count = 0;
    public static ServletContext application;
	
	@Autowired
	private SysService sysService;
	
	//用户登陆提交方法
	/**
	 * @param session
	 * @param randomcode 输入的验证码
	 * @param usercode 用户账号
	 * @param password 用户密码 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String login(HttpSession session, HttpServletResponse response, String randomcode,String loginName, String loginPwd)throws Exception{
		//校验验证码，防止恶性攻击
		//从session获取正确验证码
		String validateCode = (String) session.getAttribute("validateCode");
		//输入的验证和session中的验证进行对比 
		if(!randomcode.equals(validateCode)){
		    return writeAjaxResponse(JSONUtil.result(false, "验证码输入错误", "", ""), response);
		}
		//调用service校验用户账号和密码的正确性
		Member member;
        try {
            member = sysService.authenticat(loginName, loginPwd);
        } catch (Exception e) {
            return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""), response);
        }
		//如果service校验通过，将用户身份记录到session
		session.setAttribute("member", member);
		//统计在线登录用户人数
		if (application == null) {
		    application = session.getServletContext();
		    application.setAttribute("count", count);
		}
		application.setAttribute("count", ((Integer)application.getAttribute("count")) + 1);
		//重定向到系统欢迎页面
		return writeAjaxResponse(JSONUtil.result(true , "", "", ""), response);
	}

	//用户退出
	@RequestMapping("/logout")
	public String logout(HttpSession session)throws Exception{
		//session失效
		session.invalidate();
		application.setAttribute("count", ((Integer)application.getAttribute("count")) - 1);
		//重定向到首页
		return "redirect:/welcome.action";
		
	}
}
