package cn.prozhu.ssm.service.membercard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import cn.prozhu.ssm.exception.CustomException;
import cn.prozhu.ssm.mapper.cardchargerecord.CardRechargeRecordMapper;
import cn.prozhu.ssm.mapper.cardrecord.CardRecordMapper;
import cn.prozhu.ssm.mapper.consumerecord.ConsumeRecordMapper;
import cn.prozhu.ssm.mapper.discount.DiscountMapper;
import cn.prozhu.ssm.mapper.member.MemberMapper;
import cn.prozhu.ssm.mapper.membercard.MemberCardMapper;
import cn.prozhu.ssm.mapper.pointrecord.PointRecordMapper;
import cn.prozhu.ssm.model.cardchargerecord.CardRechargeRecord;
import cn.prozhu.ssm.model.cardrecord.CardRecord;
import cn.prozhu.ssm.model.consumerecord.ConsumeRecord;
import cn.prozhu.ssm.model.discount.Discount;
import cn.prozhu.ssm.model.member.Member;
import cn.prozhu.ssm.model.member.MemberExample;
import cn.prozhu.ssm.model.member.MemberExample.Criteria;
import cn.prozhu.ssm.model.membercard.MemberCard;
import cn.prozhu.ssm.model.membercard.MemberCardExample;
import cn.prozhu.ssm.model.pointrecord.PointRecord;
import cn.prozhu.ssm.service.member.MemberService;
import cn.prozhu.ssm.util.MailSenderUtil;
import cn.prozhu.ssm.util.RandomUtils;
import cn.prozhu.ssm.util.StringUitl;

public class MemberCardServiceImpl implements MemberCardService {
    
    @Autowired
    private MemberCardMapper memberCardMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private CardRecordMapper cardRecordMapper;
    @Autowired
    private PointRecordMapper pointRecordMapper;
    @Autowired
    private ConsumeRecordMapper consumeRecordMapper;
    @Autowired
    private CardRechargeRecordMapper cardRechargeRecordMapper;
    @Autowired
    private DiscountMapper discountMapper;
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
        
        MemberCard memberCard = new MemberCard();
        Member member = new Member();
      //2. 对会员卡的其他属性进行赋值
        //会员卡状态 ：默认  ：1 禁用
        memberCard.setCardStatus((short)1);
        //会员卡级别： 青铜(默认)
        memberCard.setCardGrade("青铜");
        //会员卡重新补办新卡，需要将旧卡的相关信息填充到这里
        if ( idList.size() == 1 && idList != null) {
            MemberCardExample example = new MemberCardExample();
            MemberCardExample.Criteria criteria = example.createCriteria();
            //需要通过id 查询出会员编号
            String memberid = memberService.findMemberById(idList.get(0)).getMemberid();
            //根据会员编号查询出老卡的信息并对新卡赋值
            criteria.andMemberidEqualTo(memberid);
            //为了兼容性，需要
            if (memberCardMapper.selectByExample(example).size() == 0 || memberCardMapper.selectByExample(example) == null) {
                memberCard.setDiscount(0L);
                memberCard.setBalance(new BigDecimal(0));
                memberCard.setTotalPoint(0);
                memberCard.setTotalConsumption(new BigDecimal(0));
                memberCard.setTotalRecharge(0L);
            } else {
                MemberCard oldCard = memberCardMapper.selectByExample(example).get(memberCardMapper.selectByExample(example).size() - 1);
                memberCard.setDiscount(oldCard.getDiscount());
                memberCard.setBalance(oldCard.getBalance());
                memberCard.setTotalPoint(oldCard.getTotalPoint());
                memberCard.setTotalConsumption(oldCard.getTotalConsumption());
                memberCard.setTotalRecharge(oldCard.getTotalRecharge());
                memberCard.setCardGrade(oldCard.getCardGrade());
            }
        } else {
            memberCard.setDiscount(0L);
            memberCard.setBalance(new BigDecimal(0));
            memberCard.setTotalPoint(0);
            memberCard.setTotalConsumption(new BigDecimal(0));
            memberCard.setTotalRecharge(0L);
        }

        for (String id : idList) {
            member = memberService.findMemberById(id);
            memberCard.setMemberCardId(RandomUtils.getSerialNumber());
            memberCard.setOpenDate(new Date());
            memberCard.setMemberName(member.getMembername());
            memberCard.setMemberId(member.getMemberid());
            memberCardMapper.insert(memberCard);
        }
    }

    @Override
    public List<String> isHasMemberCard(String[] ids,   MemberService memberService) {
        String memberid = "";
        List<String> idList = new ArrayList<String>();
        List<MemberCard> memberCardList = null;
        
      //1. 首先通过id查询出 会员的编号
        for (String id : ids) {
            MemberCardExample example = new MemberCardExample();
            MemberCardExample.Criteria criteria = example.createCriteria();
            memberid = memberService.findMemberById(id).getMemberid();
          //2. 查询用户是否已经办了卡(看表中是否存在该会员编号)
            criteria.andMemberidEqualTo(memberid);
            memberCardList = memberCardMapper.selectByExample(example);
            
            if (memberCardList == null || memberCardList.size() == 0) {
                idList.add(id);
            } else {
                //表示该会员已经办理过了会员卡（但是卡状态在注销的情况下，是可以重新办理会员卡的），并且不能批量办卡
                if (ids.length == 1) {
                    if (memberCardList.get(memberCardList.size() - 1).getCardStatus() == 3) {
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
    public List<MemberCard> findMemberCardByMemberId(String memberid) {
        MemberCardExample memberCardExample = new MemberCardExample();
        MemberCardExample.Criteria criteria = memberCardExample.createCriteria();
        criteria.andCardstatusNotEqualTo((short)3);
        criteria.andMemberidEqualTo(memberid);
        if (!StringUitl.isNullOrEmpty(memberid)) {
            return memberCardMapper.selectByExample(memberCardExample);
        }
        return null;
    }
    
    
    public MemberCard findMemberCardById(String id) {
        if (!StringUitl.isNullOrEmpty(id)) {
            return memberCardMapper.selectByPrimaryKey(new Long(id));
        }
        return null;
    }
    
    @Cacheable(value="MemberCard.findMemberCardByCondition", 
	key = "'MemberCard.findMemberCardByCondition'+#sort + #order+#pageNow+#pageSize+#pageSize",
	condition = "null == #startTime and null == #endTime and null == #keyword ")
    @Override
    public List<MemberCard> findMemberCardByCondition(String pageNow, String pageSize, String startTime, String endTime,
            String keyword, String sort, String order, MemberService memberService) {
        if (pageNow == null) {
            pageNow = "1";
        }
        if (pageSize == null) {
            pageSize = "100000";
        }
        MemberCardExample memberCardExample = new MemberCardExample();
        MemberCardExample.Criteria criteria = memberCardExample.createCriteria();
        if (!StringUitl.isNullOrEmpty(sort) && !StringUitl.isNullOrEmpty(order)) {
            memberCardExample.setOrderByClause(sort + " " + order);
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
        List<MemberCard> list = memberCardMapper.selectByExampleWithRowbounds(memberCardExample, rowBounds);
        if (list.size() >  0 && list != null) {
            return list;
        }
        return null;
    }


    @Override
    public Integer getCount(String startTime, String endTime, String keyword) {
        MemberCardExample memberCardExample = new MemberCardExample();
        MemberCardExample.Criteria criteria = memberCardExample.createCriteria();
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
        return memberCardMapper.countByExample(memberCardExample);
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
        MemberCard memberCard = findMemberCardById(id);
        //查询出用户的邮箱
        List<Member> list = queryUserEmail(memberCard);
		//积分调整
        pointAdjust(point, memberCard, list);
        //会员卡充值
        memberCardRecharge(recharge, memberCard, list);
        //会员卡消费
        memberCardConsume(consume, memberCard, list);
        return memberCardMapper.updateByPrimaryKey(memberCard);
    }

	/**
	 * 会员卡消费
	 * @param consume
	 * @param memberCard
	 * @param list
	 * @throws CustomException
	 */
	private void memberCardConsume(String consume, MemberCard memberCard,
			List<Member> list) throws CustomException {
		//是否消费
        if (!StringUitl.isNullOrEmpty(consume)) {
            if (memberCard.getBalance().compareTo(new BigDecimal(consume)) < 0) {
                throw new CustomException("余额不足");
            }
            
            // 根据会员的级别 和 相应的折扣 ，来计算最终消费的金额
            Discount discount = discountMapper.queyDiscountByGrade(memberCard.getCardGrade());
            memberCard.setBalance(memberCard.getBalance()
            		.subtract((new BigDecimal(consume)
            		.multiply(new BigDecimal(discount.getDiscount())))));
            memberCard.setTotalConsumption(memberCard.getTotalConsumption()
            		.add((new BigDecimal(consume)
            		.multiply(new BigDecimal(discount.getDiscount())))));
            //这里更改会员的级别和折扣(需要遍历折扣表)
			List<Discount> dis = discountMapper.queryAllDiscount();
			for (int i = 0; i < dis.size(); i++) {
				if (memberCard.getTotalConsumption().compareTo(
						dis.get(i).getHighConsume()) < 0
						&& memberCard.getTotalConsumption().compareTo(
								dis.get(i).getLowConsume()) >= 0) {
					memberCard.setCardGrade(dis.get(i).getGrade());
					memberCard.setDiscount(dis.get(i).getDiscount());
				}
			}
			//为会员卡添加消费记录
            ConsumeRecord consumeRecord = addConsumeRecord(consume, memberCard,
					discount);
            //为会员卡添加积分记录信息
            addPointRecord(consume, memberCard, list, consumeRecord);
        }
	}

	/**
	 * 为会员卡添加消费记录
	 * @param consume
	 * @param memberCard
	 * @param discount
	 * @return
	 */
	private ConsumeRecord addConsumeRecord(String consume,
			MemberCard memberCard, Discount discount) {
		//添加消费记录
		ConsumeRecord consumeRecord = new ConsumeRecord();
		//消费金额涉及到打折，需要和会员级别进行区分
		consumeRecord.setDiscountMoney(new BigDecimal(consume));
		consumeRecord.setConsumeMoney(new BigDecimal(consume).multiply(new BigDecimal(discount.getDiscount())));
		consumeRecord.setConsumeTime(new Date());
		consumeRecord.setMemberCardId(memberCard.getMemberCardId());
		consumeRecord.setMemberId(memberCard.getMemberId());
		consumeRecord.setMemberName(memberCard.getMemberName());
		consumeRecordMapper.insert(consumeRecord);
		return consumeRecord;
	}

	/**
	 * 为会员卡添加积分记录信息
	 * @param consume
	 * @param memberCard
	 * @param list
	 * @param consumeRecord
	 */
	private void addPointRecord(String consume, MemberCard memberCard,
			List<Member> list, ConsumeRecord consumeRecord) {
		//为会员卡添加积分信息
		memberCard.setTotalPoint(memberCard.getTotalPoint() + (int)(Math.ceil(Double.parseDouble(consume))));
		//添加积分记录
		PointRecord pointRecord = new PointRecord();
		pointRecord.setChangeTime(new Date());
		pointRecord.setOperationType("消费获取");
		pointRecord.setPoints((int)(Math.ceil(Double.parseDouble(consume))));
		pointRecord.setMemberCardId(memberCard.getMemberCardId());
		pointRecord.setMemberId(memberCard.getMemberId());
		pointRecord.setMemberName(memberCard.getMemberName());
		if (pointRecordMapper.insert(pointRecord) > 0) {
			if (!StringUitl.isNullOrEmpty(list.get(0).getEmail())) {
				mailSenderUtil.send(list.get(0).getEmail(), "会员卡消费获取积分提醒！", "尊敬的用户，您卡号为: " 
			+ memberCard.getMemberCardId() + " 的会员卡在" 
			+ RandomUtils.formatTime(new Date())+" 消费了" + consumeRecord.getConsumeMoney() + "元, 获取了" + pointRecord.getPoints() +"积分！" );
			}
		}
	}

	/**
	 * 会员卡充值
	 * @param recharge
	 * @param memberCard
	 * @param list
	 */
	private void memberCardRecharge(String recharge, MemberCard memberCard,
			List<Member> list) {
		//是否充值
        if (!StringUitl.isNullOrEmpty(recharge)) {
            CardRechargeRecord card = new CardRechargeRecord();
            memberCard.setBalance(memberCard.getBalance().add(new BigDecimal(recharge)));
            memberCard.setTotalRecharge(memberCard.getTotalRecharge() + new Long(recharge));
            //添加充值的记录
            card.setChangeTime(new Date());
            card.setMemberCardId(memberCard.getMemberCardId());
            card.setMemberId(memberCard.getMemberId());
            card.setMemberName(memberCard.getMemberName());
            card.setRechargeMoney(new Long(recharge));
            if (cardRechargeRecordMapper.insert(card) > 0) {
            	if (!StringUitl.isNullOrEmpty(list.get(0).getEmail())) {
					mailSenderUtil.send(list.get(0).getEmail(), "会员卡充值提醒！", "尊敬的用户，您的卡号为：" 
            	+ memberCard.getMemberCardId() 
							+ " 的会员卡在" + RandomUtils.formatTime(new Date()) +"充值了 " + recharge + "元");
				}
            }
        }
	}

	/**
	 * 查询用户的邮箱地址
	 * @param memberCard
	 * @return
	 */
	private List<Member> queryUserEmail(MemberCard memberCard) {
		//查询出会员的邮箱，以便于邮件的发送
        MemberExample me = new MemberExample();
		Criteria createCriteria = me.createCriteria();
		createCriteria.andMemberidEqualTo(memberCard.getMemberId());
		List<Member> list = memberMapper.selectByExample(me);
		return list;
	}

	/**
	 * 会员卡积分调整
	 * @param point
	 * @param memberCard
	 * @param list
	 * @throws CustomException
	 */
	private void pointAdjust(String point, MemberCard memberCard,
			List<Member> list) throws CustomException {
		//是否积分调整
        if (!StringUitl.isNullOrEmpty(point)) {
            if (memberCard.getTotalPoint() + Integer.parseInt(point) < 0) {
                throw new CustomException("积分调整失败");
            }
            memberCard.setTotalPoint(memberCard.getTotalPoint() + Integer.parseInt(point));
            //添加手工调整的积分记录
            PointRecord pointRecord = new PointRecord();
            pointRecord.setChangeTime(new Date());
            pointRecord.setOperationType("异常调整");
            pointRecord.setPoints(Integer.parseInt(point));
            pointRecord.setMemberCardId(memberCard.getMemberCardId());
            pointRecord.setMemberId(memberCard.getMemberId());
            pointRecord.setMemberName(memberCard.getMemberName());
			if (pointRecordMapper.insert(pointRecord) > 0) {
				if (!StringUitl.isNullOrEmpty(list.get(0).getEmail())) {
					mailSenderUtil.send(list.get(0).getEmail(), "会员卡积分提醒！", "尊敬的用户，由于某些原因, 在 "
				+ RandomUtils.formatTime(new Date())+" 您卡号为："+ memberCard.getMemberCardId()
				+" 的会员卡积分调整了 " + point + "积分，请周知！");
				}
			}
		}
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
        CardRecord cardRecord = new CardRecord();
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
        MemberCard memberCard = new MemberCard();
        for(String id : idList) {
            memberCard = memberCardMapper.selectByPrimaryKey(new Long(id));
            List<Member> list = queryUserEmail(memberCard);
    		
            //需要保存会员卡激活、挂失记录
            cardRecord.setChangeTime(new Date());
            cardRecord.setMemberCardId(memberCard.getMemberCardId());
            cardRecord.setMemberId(memberCard.getMemberId());
            cardRecord.setMemberName(memberCard.getMemberName());
            if ("activate".equals(flag)) {
                cardRecord.setOperationType("激活");
                memberCard.setCardStatus((short)0);
            } else if ("loss".equals(flag)) {
                cardRecord.setOperationType("挂失");
                memberCard.setCardStatus((short)2);
            } else if ("unloss".equals(flag)) {
                cardRecord.setOperationType("解除挂失");
                memberCard.setCardStatus((short)0);
            } else {
                cardRecord.setOperationType("注销");
                memberCard.setCardStatus((short)3);
            }
            memberCardMapper.updateByPrimaryKey(memberCard);
            //cardRecordMapper.insert(cardrecord);
            //会员卡的激活、注销、挂失、解除挂失操作都通过邮箱通知用户
            if (cardRecordMapper.insert(cardRecord) > 0) {
            	if (!StringUitl.isNullOrEmpty(list.get(0).getEmail())) {
					mailSenderUtil.send(list.get(0).getEmail(), "会员卡状态变更提醒！", "尊敬的用户，您卡号为: " 
            	+ memberCard.getMemberCardId() + " 的会员卡在 :" 
            	+ RandomUtils.formatTime(new Date())+" 时，被" + cardRecord.getOperationType() + "了,请周知！"  );
				}
            }
        }
    }
    
    public List<String> unActivateMemberCardId(String[] ids, String flag) {
        // 保存需要激活的会员卡id
        List<String> idList = new ArrayList<String>();
        // 可以批量的激活会员卡
        MemberCard memberCard = new MemberCard();
        // 判断该卡是否激活 卡状态(0正常，1禁用，2挂失)
        // 只有在 1状态下，才可以激活
        if ("activate".equals(flag)) {
            for (String id : ids) {
                memberCard = memberCardMapper.selectByPrimaryKey(new Long(id));
                if (memberCard.getCardStatus() != 0 && memberCard.getCardStatus() != 2) {
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
                memberCard = memberCardMapper.selectByPrimaryKey(new Long(id));
                if (memberCard.getCardStatus() != 2 && memberCard.getCardStatus() != 1) {
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
                memberCard = memberCardMapper.selectByPrimaryKey(new Long(id));
                if (memberCard.getCardStatus() != 0 && memberCard.getCardStatus() != 1) {
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
                memberCard = memberCardMapper.selectByPrimaryKey(new Long(id));
                if (memberCard.getCardStatus() != 1) {
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

	@Override
	public List<Map<String, Object>> memberCardChart(String mark, String markYear, String time) {
		if (StringUitl.isNullOrEmpty(mark.trim())) {
			return null;
		}
		List<Map<String, Object>> map = null;
		if ("year".equals(mark.trim())) {
			map = memberCardMapper.memberCardChartByYear();
		} else if ("quarter".equals(mark.trim())) {
			map = memberCardMapper.memberCardChartByQuarter(markYear);
		} else if ("month".equals(mark.trim())) {
			map = memberCardMapper.memberCardChartByMonth(markYear);
		} else {
				map = memberCardMapper.memberCardChartByWeek(StringUitl.isNullOrEmpty(time)? RandomUtils.formatTime1(new Date()) : time);
		}
		if (StringUitl.isNullOrEmpty(map)) {
			return null;
		}
		return map;
	}

	@Override
	public List<String> findMemberCardYears() {
		List<String> list = memberCardMapper.selectYears();
		if (StringUitl.isNullOrEmpty(list)) {
			return null;
		}
		return list;
	}

}






