package cn.itcast.ssm.service.membercard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.ssm.exception.CustomException;
import cn.itcast.ssm.mapper.cardchargerecord.CardrechargerecordMapper;
import cn.itcast.ssm.mapper.cardrecord.CardrecordMapper;
import cn.itcast.ssm.mapper.consumerecord.ConsumerecordMapper;
import cn.itcast.ssm.mapper.member.MemberMapper;
import cn.itcast.ssm.mapper.membercard.MembercardMapper;
import cn.itcast.ssm.mapper.pointrecord.PointrecordMapper;
import cn.itcast.ssm.model.cardchargerecord.Cardrechargerecord;
import cn.itcast.ssm.model.cardrecord.Cardrecord;
import cn.itcast.ssm.model.consumerecord.Consumerecord;
import cn.itcast.ssm.model.member.Member;
import cn.itcast.ssm.model.member.MemberExample;
import cn.itcast.ssm.model.member.MemberExample.Criteria;
import cn.itcast.ssm.model.membercard.Membercard;
import cn.itcast.ssm.model.membercard.MembercardExample;
import cn.itcast.ssm.model.pointrecord.Pointrecord;
import cn.itcast.ssm.service.member.MemberService;
import cn.itcast.ssm.util.MailSenderUtil;
import cn.itcast.ssm.util.RandomUtils;
import cn.itcast.ssm.util.StringUitl;

public class MemberCardServiceImpl implements MemberCardService {
    
    @Autowired
    private MembercardMapper membercardMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private CardrecordMapper cardrecordMapper;
    @Autowired
    private PointrecordMapper pointrecordMapper;
    @Autowired
    private ConsumerecordMapper consumerecordMapper;
    @Autowired
    private CardrechargerecordMapper cardrechargerecordMapper;
	@Autowired
	private MailSenderUtil mailSenderUtil;
	
	
    @Transactional
    @CacheEvict(value="MemberCard.findMemberCardByCondition",allEntries=true) 
    @Override
    public void saveMemberCard(String ids, MemberService memberService) throws Exception {
        String[] idd = null;
        if(!StringUitl.isNullOrEmpty(ids)) {
          //将多个ids分割为数组
            idd = ids.split(",");
        } else {
            throw new Exception("信息错误！");
        }
        List<String> idList = isHasMemberCard(idd, memberService);
        if (idList == null || idList.size() == 0) {
            throw new Exception("您已经办理过会员卡，请不要重复办理！");
        }
        
        Membercard membercard = new Membercard();
        Member member = new Member();
      //2. 对会员卡的其他属性进行赋值
        //会员卡状态 ：默认  ：1 禁用
        membercard.setCardstatus((short)1);
        //会员卡级别： 青铜(默认)
        membercard.setCardgrade("青铜");
        //会员卡重新补办新卡，需要将旧卡的相关信息填充到这里
        if ( idList.size() == 1 && idList != null) {
            MembercardExample example = new MembercardExample();
            MembercardExample.Criteria criteria = example.createCriteria();
            //需要通过id 查询出会员编号
            String memberid = memberService.findMemberById(idList.get(0)).getMemberid();
            //根据会员编号查询出老卡的信息并对新卡赋值
            criteria.andMemberidEqualTo(memberid);
            //为了兼容性，需要
            if (membercardMapper.selectByExample(example).size() == 0 || membercardMapper.selectByExample(example) == null) {
                membercard.setDiscount(0L);
                membercard.setBalance(new BigDecimal(0));
                membercard.setTotalpoint(0);
                membercard.setTotalconsumption(new BigDecimal(0));
                membercard.setTotalrecharge(0L);
            } else {
                Membercard oldCard = membercardMapper.selectByExample(example).get(membercardMapper.selectByExample(example).size() - 1);
                membercard.setDiscount(oldCard.getDiscount());
                membercard.setBalance(oldCard.getBalance());
                membercard.setTotalpoint(oldCard.getTotalpoint());
                membercard.setTotalconsumption(oldCard.getTotalconsumption());
                membercard.setTotalrecharge(oldCard.getTotalrecharge());
                membercard.setCardgrade(oldCard.getCardgrade());
            }
        } else {
            membercard.setDiscount(0L);
            membercard.setBalance(new BigDecimal(0));
            membercard.setTotalpoint(0);
            membercard.setTotalconsumption(new BigDecimal(0));
            membercard.setTotalrecharge(0L);
        }

        for (String id : idList) {
            member = memberService.findMemberById(id);
            membercard.setMembercardid(RandomUtils.getSerialNumber());
            membercard.setOpendate(new Date());
            membercard.setMembername(member.getMembername());
            membercard.setMemberid(member.getMemberid());
            membercardMapper.insert(membercard);
        }
    }

    @Override
    public List<String> isHasMemberCard(String[] ids,   MemberService memberService) {
        String memberid = "";
        List<String> idList = new ArrayList<String>();
        List<Membercard> memberCardList = null;
        
      //1. 首先通过id查询出 会员的编号
        for (String id : ids) {
            MembercardExample example = new MembercardExample();
            MembercardExample.Criteria criteria = example.createCriteria();
            memberid = memberService.findMemberById(id).getMemberid();
          //2. 查询用户是否已经办了卡(看表中是否存在该会员编号)
            criteria.andMemberidEqualTo(memberid);
            memberCardList = membercardMapper.selectByExample(example);
            
            if (memberCardList == null || memberCardList.size() == 0) {
                idList.add(id);
            } else {
                //表示该会员已经办理过了会员卡（但是卡状态在注销的情况下，是可以重新办理会员卡的），并且不能批量办卡
                if (ids.length == 1) {
                    if (memberCardList.get(memberCardList.size() - 1).getCardstatus() == 3) {
                        idList.add(id);
                    } else {
                        return null;
                    }
                }
            }
        }
        return idList;
    }
    
    @Cacheable(value="MemberCard.findMemberCardByMemberId", 
    		key = "'MemberCard.findMemberCardByMemberId'+#memberid")
    @Override
    public List<Membercard> findMemberCardByMemberId(String memberid) {
        MembercardExample membercardExample = new MembercardExample();
        MembercardExample.Criteria criteria = membercardExample.createCriteria();
        criteria.andCardstatusNotEqualTo((short)3);
        criteria.andMemberidEqualTo(memberid);
        if (!StringUitl.isNullOrEmpty(memberid)) {
            return membercardMapper.selectByExample(membercardExample);
        }
        return null;
    }
    
    
    public Membercard findMemberCardById(String id) {
        if (!StringUitl.isNullOrEmpty(id)) {
            return membercardMapper.selectByPrimaryKey(new Long(id));
        }
        return null;
    }
    
    @Cacheable(value="MemberCard.findMemberCardByCondition", 
	key = "'MemberCard.findMemberCardByCondition'+#sort + #order+#pageNow+#pageSize+#pageSize",
	condition = "null == #startTime and null == #endTime and null == #keyword ")
    @Override
    public List<Membercard> findMemberCardByCondition(String pageNow, String pageSize, String startTime, String endTime,
            String keyword, String sort, String order, MemberService memberService) {
        if (pageNow == null) {
            pageNow = "1";
        }
        if (pageSize == null) {
            pageSize = "100000";
        }
        MembercardExample membercardExample = new MembercardExample();
        MembercardExample.Criteria criteria = membercardExample.createCriteria();
        if (!StringUitl.isNullOrEmpty(sort) && !StringUitl.isNullOrEmpty(order)) {
            membercardExample.setOrderByClause(sort + " " + order);
        }
        //时间查询
        if (!StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andOpendateBetween(RandomUtils.dateFromString(startTime), RandomUtils.dateFromString(endTime));
        } else if (StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andOpendateLessThanOrEqualTo(RandomUtils.dateFromString(endTime));
        } else if (!StringUitl.isNullOrEmpty(startTime) && StringUitl.isNullOrEmpty(endTime)) {
            criteria.andOpendateGreaterThanOrEqualTo(RandomUtils.dateFromString(startTime));
        } 
        
        //会员卡状态（状态不等于3（注销）） 状态为3（注销）的会员卡不显示
        criteria.andCardstatusNotEqualTo((short)3);
        
        //会员名称
        if (!StringUitl.isNullOrEmpty(keyword)) {
            criteria.andMembernameLike("%" + keyword + "%");
        }
        
        RowBounds rowBounds = new RowBounds((Integer.parseInt(pageNow) -1) *  Integer.parseInt(pageSize), Integer.parseInt(pageSize));
        List<Membercard> list = membercardMapper.selectByExampleWithRowbounds(membercardExample, rowBounds);
        if (list.size() >  0 && list != null) {
            return list;
        }
        return null;
    }


    @Override
    public Integer getCount(String startTime, String endTime, String keyword) {
        MembercardExample membercardExample = new MembercardExample();
        MembercardExample.Criteria criteria = membercardExample.createCriteria();
        //时间查询
        if (!StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andOpendateBetween(RandomUtils.dateFromString(startTime), RandomUtils.dateFromString(endTime));
        } else if (StringUitl.isNullOrEmpty(startTime) && !StringUitl.isNullOrEmpty(endTime)) {
            criteria.andOpendateLessThanOrEqualTo(RandomUtils.dateFromString(endTime));
        } else if (!StringUitl.isNullOrEmpty(startTime) && StringUitl.isNullOrEmpty(endTime)) {
            criteria.andOpendateGreaterThanOrEqualTo(RandomUtils.dateFromString(startTime));
        }
        // 会员级别
        if (!StringUitl.isNullOrEmpty(keyword)) {
            criteria.andCardgradeLike("%" + keyword + "%");
        }
        return membercardMapper.countByExample(membercardExample);
    }

   
    @CacheEvict(value={
    		"CardRechargeRecord.findCardRechargeRecordByMemberIdAndCondition", 
    		"CardRecord.findCardRecordByMemberIdAndCondition"
    		, "ConsumeRecord.findConsumeRecordByMemberIdAndCondition",
    		"PointRecord.findPointRecordByMemberIdAndCondition"
    		, "MemberCard.findMemberCardByMemberId", 
    		"MemberCard.findMemberCardByCondition"},allEntries=true)
    @Transactional
    @Override
    public Integer updateMemberCard(String recharge, String consume, String id, String point) throws CustomException {
        //1. 首先通过id 查询会员卡信息
        Membercard memberCard = findMemberCardById(id);
        //查询出会员的邮箱，以便于邮件的发送
        MemberExample me = new MemberExample();
		Criteria createCriteria = me.createCriteria();
		createCriteria.andMemberidEqualTo(memberCard.getMemberid());
		List<Member> list = memberMapper.selectByExample(me);
       
        //是否积分调整
        if (!StringUitl.isNullOrEmpty(point)) {
            if (memberCard.getTotalpoint() + Integer.parseInt(point) < 0) {
                throw new CustomException("积分调整失败");
            }
            memberCard.setTotalpoint(memberCard.getTotalpoint() + Integer.parseInt(point));
            //添加手工调整的积分记录
            Pointrecord pointRecord = new Pointrecord();
            pointRecord.setChangetime(new Date());
            pointRecord.setOperationtype("异常调整");
            pointRecord.setPoints(Integer.parseInt(point));
            pointRecord.setMembercardid(memberCard.getMembercardid());
            pointRecord.setMemberid(memberCard.getMemberid());
            pointRecord.setMembername(memberCard.getMembername());
			if (pointrecordMapper.insert(pointRecord) > 0) {
				if (!StringUitl.isNullOrEmpty(list.get(0).getEmail())) {
					mailSenderUtil.send(list.get(0).getEmail(), "会员卡积分提醒！", "尊敬的用户，由于某些原因, 在 "
				+ RandomUtils.formatTime(new Date())+" 您卡号为："+ memberCard.getMembercardid()
				+" 的会员卡积分调整了 " + point + "积分，请周知！");
				}
			}
		}
        
        //是否充值
        if (!StringUitl.isNullOrEmpty(recharge)) {
            Cardrechargerecord card = new Cardrechargerecord();
            memberCard.setBalance(memberCard.getBalance().add(new BigDecimal(recharge)));
            memberCard.setTotalrecharge(memberCard.getTotalrecharge() + new Long(recharge));
            //添加充值的记录
            card.setChangetime(new Date());
            card.setMembercardid(memberCard.getMembercardid());
            card.setMemberid(memberCard.getMemberid());
            card.setMembername(memberCard.getMembername());
            card.setRechargemoney(new Long(recharge));
            if (cardrechargerecordMapper.insert(card) > 0) {
            	if (!StringUitl.isNullOrEmpty(list.get(0).getEmail())) {
					mailSenderUtil.send(list.get(0).getEmail(), "会员卡充值提醒！", "尊敬的用户，您的卡号为：" 
            	+ memberCard.getMembercardid() 
							+ " 的会员卡在" + RandomUtils.formatTime(new Date()) +"充值了 " + recharge + "元");
				}
            }
        }
        //是否消费
        if (!StringUitl.isNullOrEmpty(consume)) {
            if (memberCard.getBalance().compareTo(new BigDecimal(consume)) < 0) {
                throw new CustomException("余额不足");
            }
            // 根据会员的级别 和 相应的折扣 ，来计算最终消费的金额
            if (!memberCard.getCardgrade().equals("青铜")) {
                memberCard.setBalance(memberCard.getBalance().subtract((new BigDecimal(consume).multiply(new BigDecimal(memberCard.getDiscount() / 10.0)))));
                memberCard.setTotalconsumption(memberCard.getTotalconsumption().add((new BigDecimal(consume).multiply(new BigDecimal(memberCard.getDiscount() / 10.0)))));
            } else {
                memberCard.setBalance(memberCard.getBalance().subtract(new BigDecimal(consume)));
                memberCard.setTotalconsumption(memberCard.getTotalconsumption().add(new BigDecimal(consume)));
            }
            //这里更改会员的级别
            if (memberCard.getTotalconsumption().compareTo(new BigDecimal(20000)) <= 0  && memberCard.getTotalconsumption().compareTo(new BigDecimal(10000)) > 0) {
                memberCard.setCardgrade("白银");
                memberCard.setDiscount((long)8);
            } else if (memberCard.getTotalconsumption().compareTo(new BigDecimal(20000)) > 0) {
                memberCard.setCardgrade("黄金");
                memberCard.setDiscount((long)5);
            }
            //添加消费记录
            Consumerecord consumeRecord = new Consumerecord();
            //消费金额涉及到打折，需要和会员级别进行区分
            if (!memberCard.getCardgrade().equals("青铜")) {
                consumeRecord.setConsumemoney(new BigDecimal(consume).multiply(new BigDecimal(memberCard.getDiscount() / 10.0)));
            } else {
                consumeRecord.setConsumemoney(new BigDecimal(consume));
            }
            consumeRecord.setConsumetime(new Date());
            consumeRecord.setMembercardid(memberCard.getMembercardid());
            consumeRecord.setMemberid(memberCard.getMemberid());
            consumeRecord.setMembername(memberCard.getMembername());
            consumerecordMapper.insert(consumeRecord);
 
            //为会员卡添加积分信息
            memberCard.setTotalpoint(memberCard.getTotalpoint() + (int)(Math.ceil(Double.parseDouble(consume))));
            //添加积分记录
            Pointrecord pointRecord = new Pointrecord();
            pointRecord.setChangetime(new Date());
            pointRecord.setOperationtype("消费获取");
            pointRecord.setPoints((int)(Math.ceil(Double.parseDouble(consume))));
            pointRecord.setMembercardid(memberCard.getMembercardid());
            pointRecord.setMemberid(memberCard.getMemberid());
            pointRecord.setMembername(memberCard.getMembername());
            if (pointrecordMapper.insert(pointRecord) > 0) {
            	if (!StringUitl.isNullOrEmpty(list.get(0).getEmail())) {
					mailSenderUtil.send(list.get(0).getEmail(), "会员卡消费获取积分提醒！", "尊敬的用户，您卡号为: " 
            	+ memberCard.getMembercardid() + " 的会员卡在" 
            	+ RandomUtils.formatTime(new Date())+" 消费了" + consumeRecord.getConsumemoney() + "元, 获取了" + pointRecord.getPoints() +"积分！" );
				}
            }
        }
        return membercardMapper.updateByPrimaryKey(memberCard);
    }

    @Transactional
    @CacheEvict(value="MemberCard.findMemberCardByCondition",allEntries=true) 
    @Override
    public void updateMemberCard(String ids, String flag) throws Exception {
        String[] idd = null;
        if(!StringUitl.isNullOrEmpty(ids)) {
          //将多个ids分割为数组
            idd = ids.split(",");
        } else {
            throw new CustomException("信息错误！");
        }
        Cardrecord cardrecord = new Cardrecord();
        List<String> idList = unActivateMemberCardId(idd, flag);
        if (idList == null || idList.size() == 0) {
            if ("activate".equals(flag)) {
                throw new Exception("您已经激活过会员卡，请不要重复激活！");
            } else if ("loss".equals(flag)) {
                throw new Exception("您已经挂失过会员卡，请不要重复挂失！");
            } else {
                throw new Exception("您已经解除了挂失状态，请不要重复解除！");
            }
        }
        Membercard membercard = new Membercard();
        for(String id : idList) {
            membercard = membercardMapper.selectByPrimaryKey(new Long(id));
            //查询出会员的邮箱，以便于邮件的发送
            MemberExample me = new MemberExample();
    		Criteria createCriteria = me.createCriteria();
    		createCriteria.andMemberidEqualTo(membercard.getMemberid());
    		List<Member> list = memberMapper.selectByExample(me);
    		
            //需要保存会员卡激活、挂失记录
            cardrecord.setChangetime(new Date());
            cardrecord.setMembercardid(membercard.getMembercardid());
            cardrecord.setMemberid(membercard.getMemberid());
            cardrecord.setMembername(membercard.getMembername());
            if ("activate".equals(flag)) {
                cardrecord.setOperationtype("激活");
                membercard.setCardstatus((short)0);
            } else if ("loss".equals(flag)) {
                cardrecord.setOperationtype("挂失");
                membercard.setCardstatus((short)2);
            } else if ("unloss".equals(flag)) {
                cardrecord.setOperationtype("解除挂失");
                membercard.setCardstatus((short)0);
            } else {
                cardrecord.setOperationtype("注销");
                membercard.setCardstatus((short)3);
            }
            membercardMapper.updateByPrimaryKey(membercard);
            //cardrecordMapper.insert(cardrecord);
            //会员卡的激活、注销、挂失、解除挂失操作都通过邮箱通知用户
            if (cardrecordMapper.insert(cardrecord) > 0) {
            	if (!StringUitl.isNullOrEmpty(list.get(0).getEmail())) {
					mailSenderUtil.send(list.get(0).getEmail(), "会员卡状态变更提醒！", "尊敬的用户，您卡号为: " 
            	+ membercard.getMembercardid() + " 的会员卡在 :" 
            	+ RandomUtils.formatTime(new Date())+" 时，被" + cardrecord.getOperationtype() + "了,请周知！"  );
				}
            }
        }
    }
    
    public List<String> unActivateMemberCardId(String[] ids, String flag) {
        // 保存需要激活的会员卡id
        List<String> idList = new ArrayList<String>();
        // 可以批量的激活会员卡
        Membercard membercard = new Membercard();
        // 判断该卡是否激活 卡状态(0正常，1禁用，2挂失)
        // 只有在 1状态下，才可以激活
        if ("activate".equals(flag)) {
            for (String id : ids) {
                membercard = membercardMapper.selectByPrimaryKey(new Long(id));
                if (membercard.getCardstatus() != 0 && membercard.getCardstatus() != 2) {
                    idList.add(id);
                } else {
                    if (ids.length == 1) {
                        return null;
                    }
                }
            }
        } else if ("loss".equals(flag)){
            //会员卡挂失
            //只有在 0(正常) 的会员卡状态下才可以挂失
            for (String id : ids) {
                membercard = membercardMapper.selectByPrimaryKey(new Long(id));
                if (membercard.getCardstatus() != 2 && membercard.getCardstatus() != 1) {
                    idList.add(id);
                } else {
                    if (ids.length == 1) {
                        return null;
                    }
                }
            }
        } else if ("unloss".equals(flag)) {
            //会员卡挂失解除
            //只有在 2(挂失) 的会员卡状态下才可以解除会员卡的挂失状态
            for (String id : ids) {
                membercard = membercardMapper.selectByPrimaryKey(new Long(id));
                if (membercard.getCardstatus() != 0 && membercard.getCardstatus() != 1) {
                    idList.add(id);
                } else {
                    if (ids.length == 1) {
                        return null;
                    }
                }
            }
        } else {
            //会员卡注销
            //会员卡在禁用(1)的状态下不允许注销
            for (String id : ids) {
                membercard = membercardMapper.selectByPrimaryKey(new Long(id));
                if (membercard.getCardstatus() != 1) {
                    idList.add(id);
                } else {
                    if (ids.length == 1) {
                        return null;
                    }
                }
            }
        }
        return idList;
    }

}






