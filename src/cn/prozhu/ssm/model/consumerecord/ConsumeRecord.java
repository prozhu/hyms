package cn.prozhu.ssm.model.consumerecord;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConsumeRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5510392696164510385L;

	private Long id;
	private String memberId; // 会员编号
	private String memberCardId; // 会员卡号
	private BigDecimal discountMoney;// 打折之前的金额
	private BigDecimal consumeMoney; // 打折之后的金额
	private String memberName; // 会员名称
	private Date consumeTime; // 消费时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberCardId() {
		return memberCardId;
	}

	public void setMemberCardId(String memberCardId) {
		this.memberCardId = memberCardId;
	}

	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}

	public BigDecimal getConsumeMoney() {
		return consumeMoney;
	}

	public void setConsumeMoney(BigDecimal consumeMoney) {
		this.consumeMoney = consumeMoney;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Date getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}

}