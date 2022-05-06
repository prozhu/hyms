package cn.prozhu.ssm.model.pointrecord;

import java.io.Serializable;
import java.util.Date;

public class PointRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2832017390241250110L;

	private Long id;

    private String memberId;

    private String memberCardId;

    private String operationType;

    private Integer points;

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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
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