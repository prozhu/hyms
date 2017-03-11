package cn.itcast.ssm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.ssm.exception.CustomException;
import cn.itcast.ssm.mapper.CardrechargerecordMapper;
import cn.itcast.ssm.mapper.CardrecordMapper;
import cn.itcast.ssm.mapper.ConsumerecordMapper;
import cn.itcast.ssm.mapper.MemberMapper;
import cn.itcast.ssm.mapper.MembercardMapper;
import cn.itcast.ssm.mapper.PointrecordMapper;
import cn.itcast.ssm.model.Cardrechargerecord;
import cn.itcast.ssm.model.CardrechargerecordExample;
import cn.itcast.ssm.model.Cardrecord;
import cn.itcast.ssm.model.CardrecordExample;
import cn.itcast.ssm.model.Consumerecord;
import cn.itcast.ssm.model.ConsumerecordExample;
import cn.itcast.ssm.model.Member;
import cn.itcast.ssm.model.MemberExample;
import cn.itcast.ssm.model.Membercard;
import cn.itcast.ssm.model.MembercardExample;
import cn.itcast.ssm.model.Pointrecord;
import cn.itcast.ssm.model.PointrecordExample;
import cn.itcast.ssm.service.MemberService;
import cn.itcast.ssm.util.MD5;
import cn.itcast.ssm.util.MailSenderUtil;
import cn.itcast.ssm.util.RandomUtils;
import cn.itcast.ssm.util.StringUitl;

public class MemberServiceImpl implements MemberService {

    //注入mapper代理对象
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private ConsumerecordMapper consumerecordMapper;
    @Autowired
    private PointrecordMapper pointrecordMapper;
    @Autowired
    private CardrechargerecordMapper cardrechargerecordMapper;
    @Autowired
    private MembercardMapper membercardMapper;
    @Autowired
    private CardrecordMapper cardrecordMapper;
	@Autowired
	private MailSenderUtil mailSenderUtil;
	
  
    @Transactional
    @Override
    public Integer save(Member member) {
			// 设置会员的类型（管理员或会员）
			member.setMemberid(RandomUtils.getUUID());
			member.setMembertype("1");
			member.setRemark(StringUitl.isNullOrEmpty(member.getRemark())?"": member.getRemark());
			member.setRegistertime(new Date());
			member.setState((short) 0);
			member.setEmail(StringUitl.isNullOrEmpty(member.getEmail())?"": member.getEmail());
			if (member != null) {
				// 密码md5 加密
				member.setLoginpwd(new MD5().getMD5ofStr(member.getLoginpwd()));
				return memberMapper.insert(member);
			}
			return 0;
	}

    
    /**
     * 对会员集合中的时间和会员类型进行格式化
     * @param mList
     * @return
     */
	public List<Member> formatMember(List<Member> mList) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for(Member member : mList) {
			//设置会员类型
			if ("1".equals(member.getMembertype())) {
				member.setMembertype("普通会员");
			} else {
				member.setMembertype("管理员");
			}
		}
		return mList;
	}

	

    @Override
    public Member findMemberById(String id) {
        if (!StringUitl.isNullOrEmpty(id)) {
            return memberMapper.selectByPrimaryKey(new Long(id));
        }
        return null;
    }

    @Transactional
    @Override
    public Integer deleteMember(String ids) throws CustomException {
        //将多个ids分割为数组
        String[] idd = ids.split(",");
        //伪删除，只是更改一下删除的标记为1
        Member member = null;
        Integer state = 0;
        MembercardExample memberCardExample = new MembercardExample();
        MembercardExample.Criteria criteria = memberCardExample.createCriteria();
        for (String id : idd) {
            member = findMemberById(id);
            //如果是管理员，就不允许删除管理员
            if ("0".equals(member.getMembertype())) {
                throw new CustomException("管理员不允许被删除！");
            }
            member.setState((short)1);
            //在删除用户的同时，需要将该用户的会员卡状态设置为注销（但是其会员卡的相关记录并不会被删除），到时候在查询会员卡时，不会被查询出来
            criteria.andMemberidEqualTo(member.getMemberid());
            Membercard memberCard = membercardMapper.selectByExample(memberCardExample).get(0);
            memberCard.setCardstatus((short)3);
            membercardMapper.updateByPrimaryKey(memberCard);
            state = memberMapper.updateByPrimaryKey(member);
        }
       return state;
    }
    

    @Override
    public List<Member> findAll(String pageNow, String pageSize) {
        MemberExample memberExample = new MemberExample();
        MemberExample.Criteria criteria = memberExample.createCriteria();
        criteria.andStateEqualTo((short)0);
        List<Member> list = new ArrayList<Member>();
        if (!StringUitl.isNullOrEmpty(pageSize) && !StringUitl.isNullOrEmpty(pageNow)) {
            RowBounds rowBounds = new RowBounds((Integer.parseInt(pageNow) -1) *  Integer.parseInt(pageSize), Integer.parseInt(pageSize));
            list = memberMapper.selectByExampleWithRowbounds(memberExample, rowBounds);
        } else {
            list = memberMapper.selectByExample(memberExample);
        }
        if (list.size() >  0 && list != null) {
            return list;
        }
        return null;
    }


   
    @Transactional
    @Override
    public Integer updateMember(Member member) {
        //1. 首先根据id查询出原始member信息
        Member m = findMemberById(member.getId().toString());
        
        //原始密码
        String oldPwd = m.getLoginpwd();
        //新密码
        String newPwd = member.getLoginpwd();
        //2. 判断密码是否被修改了
        if (!member.getLoginpwd().equals(m.getLoginpwd())) {
          //密码需要md5加密
            newPwd = new MD5().getMD5ofStr(member.getLoginpwd());
            member.setLoginpwd(newPwd);
        }
        
        //3. 判断会员的名称是否被修改了，(因为这个名称与其他积分、消费记录表的名称相同，所以需要级联更新)
        if (!member.getMembername().equals(m.getMembername())) {
            //如果名称更改，需要级联更新其他表   积分记录、消费记录、充值记录、会员卡激活挂失记录、会员卡信息表
            updateMemberName(member.getMembername(), m.getMemberid());
        }
        member.setMemberid(m.getMemberid());
        member.setMembertype(m.getMembertype());
        member.setRegistertime(m.getRegistertime());
        member.setState(m.getState());
        member.setLoginname(m.getLoginname());
        member.setEmail(StringUitl.isNullOrEmpty(member.getEmail())?"":member.getEmail());
        
        Integer state =  memberMapper.updateByPrimaryKey(member);
        
        //如果密码修改了就返回2，并强制用户重新登录
        if (!newPwd.equals(oldPwd)) {
            state = 2;
          //发送邮件通知密码被修改
            if (!StringUitl.isNullOrEmpty(m.getEmail())) {
            	mailSenderUtil.sendMail(m.getEmail(), "密码修改提醒！", "尊敬的用户，您的密码被修改了，请注意账号安全！");
            }
        }
        if (state == 0) {
            return 0;
        }
        return state;
    }

  /**
   * @description: 级联更新会员的名称
   * @author ：zc
   * @date ：2017-2-17 上午8:57:15 
   * @param membername ： 会员名称
   * @param memberid : 会员编号
   */
    @Transactional
    private void updateMemberName(String membername, String memberid) {
        //消费记录
        ConsumerecordExample consumerecordExample = new ConsumerecordExample();
        ConsumerecordExample.Criteria criteria = consumerecordExample.createCriteria();
        criteria.andMemberidEqualTo(memberid);
        //根据会员编号查询出所有消费记录
        List<Consumerecord> consumeRecordList = consumerecordMapper.selectByExample(consumerecordExample);
        for (Consumerecord record : consumeRecordList) {
            record.setMembername(membername);
            consumerecordMapper.updateByPrimaryKey(record);
        }
        
        //会员卡信息
        MembercardExample memberCardExample = new MembercardExample();
        MembercardExample.Criteria criteria1 = memberCardExample.createCriteria();
        criteria1.andMemberidEqualTo(memberid);
        //根据会员编号查询出所有消费记录
        List<Membercard> memberCardRecordList = membercardMapper.selectByExample(memberCardExample);
        for (Membercard record : memberCardRecordList) {
            record.setMembername(membername);
            membercardMapper.updateByPrimaryKey(record);
        }
        
        //会员卡激活、挂失记录
        CardrecordExample cardRecordExample = new CardrecordExample();
        CardrecordExample.Criteria criteria2 = cardRecordExample.createCriteria();
        criteria2.andMemberidEqualTo(memberid);
        //根据会员编号查询出所有消费记录
        List<Cardrecord> cardRecordList = cardrecordMapper.selectByExample(cardRecordExample);
        for (Cardrecord record : cardRecordList) {
            record.setMembername(membername);
            cardrecordMapper.updateByPrimaryKey(record);
        }
        
        //充值记录
        CardrechargerecordExample rechargeRecordExample = new CardrechargerecordExample();
        CardrechargerecordExample.Criteria criteria3 = rechargeRecordExample.createCriteria();
        criteria3.andMemberidEqualTo(memberid);
        //根据会员编号查询出所有消费记录
        List<Cardrechargerecord> rechargeRecordList = cardrechargerecordMapper.selectByExample(rechargeRecordExample);
        for (Cardrechargerecord record : rechargeRecordList) {
            record.setMembername(membername);
            cardrechargerecordMapper.updateByPrimaryKey(record);
        }
        
        //积分记录
        PointrecordExample pointRecordExample = new PointrecordExample();
        PointrecordExample.Criteria criteria4 = pointRecordExample.createCriteria();
        criteria4.andMemberidEqualTo(memberid);
        //根据会员编号查询出所有消费记录
        List<Pointrecord> pointRecordList = pointrecordMapper.selectByExample(pointRecordExample);
        for (Pointrecord record : pointRecordList) {
            record.setMembername(membername);
            pointrecordMapper.updateByPrimaryKey(record);
        }
    }


    @Override
    public List<Member> findMemberByCondition(String pageNow, String pageSize, String startTime, String endTime,
            String keyword, String sort, String order) {
        if (pageNow == null) {
            pageNow = "1";
        }
        if (pageSize == null) {
            pageSize = "100000";
        }
        MemberExample memberExample = new MemberExample();
        MemberExample.Criteria criteria = memberExample.createCriteria();
        criteria.andStateEqualTo((short)0);
        if (!StringUitl.isNullOrEmpty(sort) && !StringUitl.isNullOrEmpty(order)) {
            memberExample.setOrderByClause(sort + " " + order);
        }
        //时间查询
        if (!StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andRegistertimeBetween(RandomUtils.dateFromString(startTime), RandomUtils.dateFromString(endTime));
        } else if (StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andRegistertimeLessThanOrEqualTo(RandomUtils.dateFromString(endTime));
        } else if (!StringUitl.isNullOrEmpty(startTime) && StringUitl.isNullOrEmpty(endTime)) {
            criteria.andRegistertimeGreaterThanOrEqualTo(RandomUtils.dateFromString(startTime));
        } 
        
        //会员名称 
        if (!StringUitl.isNullOrEmpty(keyword)) {
            criteria.andMembernameLike("%" + keyword + "%");
        }
        RowBounds rowBounds = new RowBounds((Integer.parseInt(pageNow) -1) *  Integer.parseInt(pageSize), Integer.parseInt(pageSize));
        List<Member> list = memberMapper.selectByExampleWithRowbounds(memberExample, rowBounds);
        if (list.size() >  0 && list != null) {
            return list;
        }
        return null;
    }
    
    @Override
    public Integer getCount(String startTime, String endTime, String keyword) {
        MemberExample memberExample = new MemberExample();
        MemberExample.Criteria criteria = memberExample.createCriteria();
        criteria.andStateEqualTo((short)0);
        //时间查询
        if (!StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andRegistertimeBetween(RandomUtils.dateFromString(startTime), RandomUtils.dateFromString(endTime));
        } else if (StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andRegistertimeLessThanOrEqualTo(RandomUtils.dateFromString(endTime));
        } else if (!StringUitl.isNullOrEmpty(startTime) && StringUitl.isNullOrEmpty(endTime)) {
            criteria.andRegistertimeGreaterThanOrEqualTo(RandomUtils.dateFromString(startTime));
        } 
        
        //会员名称 
        if (!StringUitl.isNullOrEmpty(keyword)) {
            criteria.andMembernameLike("%" + keyword + "%");
        }
        return memberMapper.countByExample(memberExample);
    }

}
