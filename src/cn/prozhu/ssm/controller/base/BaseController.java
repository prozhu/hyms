package cn.prozhu.ssm.controller.base;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class BaseController {
	
    /**
     * @description: 向页面写回json格式的字符串
     * @author ：zc
     * @date ：2017-2-7 下午2:42:46 
     * @param ajaxString
     * @param response
     * @return
     */
    public  String writeAjaxResponse(String ajaxString, HttpServletResponse response){
        try {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(ajaxString);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally{
            return null;
        }
    }
    
}
