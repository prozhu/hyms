package cn.itcast.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.ssm.exception.CustomException;
import cn.itcast.ssm.mapper.MemberMapper;
import cn.itcast.ssm.model.Member;
import cn.itcast.ssm.model.MemberExample;
import cn.itcast.ssm.service.SysService;
import cn.itcast.ssm.util.MD5;

@Service
public class SysServiceImpl implements SysService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member authenticat(String loginName, String loginPwd)
            throws Exception {
        /**
         * 认证过程： 根据用户身份（账号）查询数据库，如果查询不到用户不存在 对输入的密码 和数据库密码 进行比对，如果一致，认证通过
         */
        // 根据登录用户名查询数据库
        Member member = this.findMemberByLoginName(loginName);
        if (member == null) {
            // 抛出异常
            throw new CustomException("账号不存在！");
        }
        
        //已经被删除的用户不允许登录 (state = 1 表示已经被删除)
        if (member.getState() == 1) {
            throw new CustomException("该账号去了二次元空间！");
        }

        // 数据库密码(md5密码)
        String pwd_db = member.getLoginpwd();

        // 对输入的密码 和数据库密码 进行比对，如果一致，认证通过
        // 对页面输入的密码 进行md5加密
        String password_input_md5 = new MD5().getMD5ofStr(loginPwd.trim());
        if (!password_input_md5.equalsIgnoreCase(pwd_db)) {
            // 抛出异常
            throw new CustomException("用户名或密码 错误");
        }
        return member;
    }

    // 根据用户账号查询用户信息
    public Member findMemberByLoginName(String loginName)  {
        // 比较新的一种用法
        MemberExample memberExample = new MemberExample();
        MemberExample.Criteria criteria = memberExample.createCriteria();
        criteria.andLoginnameEqualTo(loginName.trim());

        List<Member> list = memberMapper.selectByExample(memberExample);

        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}
