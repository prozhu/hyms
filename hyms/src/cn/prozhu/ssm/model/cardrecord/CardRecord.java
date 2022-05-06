package cn.prozhu.ssm.model.cardrecord;

import java.io.Serializable;
import java.util.Date;

public class CardRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9014618182511326100L;

	private Long id;

    private String memberId;

    private String memberCardId;

    private String operationType;

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

    public void setMemberId(String memberid) {
        this.memberId = memberid == null ? null : memberid.trim();
    }

    public String getMemberCardId() {
        return memberCardId;
    }

    public void setMemberCardId(String membercardid) {
        this.memberCardId = membercardid == null ? null : membercardid.trim();
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationtype) {
        this.operationType = operationtype == null ? null : operationtype.trim();
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changetime) {
        this.changeTime = changetime;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String membername) {
        this.memberName = membername == null ? null : membername.trim();
    }
}