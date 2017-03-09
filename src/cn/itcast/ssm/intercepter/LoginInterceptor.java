package cn.itcast.ssm.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.ssm.model.Member;

/**
 * 登录校验拦截器
 * 
 * @author Administrator
 * 
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * handler执行之前调用这个方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        //获取请求的URL
        String url = request.getRequestURI();
        
        //除了 login.action的url之外， 其他所有的URL都必须进行拦截register.action
        if (url.indexOf("login.action") >= 0 || url.indexOf("test.action") > 0 || url.indexOf("checkLoginName.action") > 0 || url.indexOf("register.action") > 0 || url.indexOf("success.action") > 0) {
            return true;
        }
        //获取session中保存的member信息
        Member member = (Member) request.getSession().getAttribute("member");
        if (member == null) {
            /**
             * 拦截目录下请求，是否为ajax请求 是：无需登录，直接访问（因为我是用于首页的ajax登录请求） 否：跳转至登录界面
             */
/*            if (request.getHeader("x-requested-with") != null
                    && request.getHeader("x-requested-with").equalsIgnoreCase(
                            "XMLHttpRequest")) {
                response.sendRedirect("/welcome.action");
                return false;
            }*/
            request.getRequestDispatcher("/welcome.action").forward(
                    request, response);// 转发到登录界面
            return false;
        } 
            return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {

    }

}
