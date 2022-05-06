package cn.prozhu.ssm.model.cardchargerecord;

import java.io.Serializable;
import java.util.Date;

public class CardRechargeRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8997029612436007510L;
	private Long id;
	private String memberId;
	private String memberCardId;
	private Long rechargeMoney;
	private Date changeTime;
	private String memberName;

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

	public Long getRechargeMoney() {
		return rechargeMoney;
	}

	public void setRechargeMoney(Long rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

}