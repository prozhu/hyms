package cn.prozhu.ssm.listener;

import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class OnLineListener
 *
 */
@WebListener
public class OnLineListener implements HttpSessionListener {
    
	public static ServletContext application = null;
    //创建session是调用
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        
    	HttpSession session = event.getSession();
    	application = event.getSession().getServletContext();
    	// 在application范围由一个HashSet集保存所有的session
        HashSet sessions = (HashSet) application.getAttribute("sessions");
        if (sessions == null) {
               sessions = new HashSet();
               application.setAttribute("sessions", sessions);
        }
        // 新创建的session均添加到HashSet集中
        sessions.add(session);
        // 可以在别处从application范围中取出sessions集合
        // 然后使用sessions.size()获取当前活动的session数，即为“在线人数”
    }

    //销毁session时调用
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    	HttpSession session = event.getSession();
        //application = session.getServletContext();
       /* HashSet sessions = (HashSet) application.getAttribute("sessions");
        // 销毁的session均从HashSet集中移除
        sessions.remove(session);*/
    	
    }

}
