package cn.itcast.ssm.service;

import cn.itcast.ssm.model.Member;


public interface SysService {

    /**
     * 
     * @description: 登录验证
     * @author ：zc
     * @date ：2017-2-6 下午2:08:19 
     * @param usercode
     * @param password
     * @return
     * @throws Exception 
     */
   public  Member authenticat(String loginName, String loginPwd) throws Exception;
   
   /**
    * 
    * @description: 校验用户名是否存在
    * @author ：zc
    * @date ：2017-2-7 下午2:33:35 
    * @param loginname
    * @return
    */
   public Member findMemberByLoginName(String loginname);

}
