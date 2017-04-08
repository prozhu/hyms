package cn.prozhu.ssm.model.cardchargerecord;

import java.io.Serializable;
import java.util.Date;

public class Cardrechargerecord implements Serializable {  
    /**
	 * 
	 */
	private static final long serialVersionUID = -8997029612436007510L;

	private Long id;

    private String memberid;

    private String membercardid;

    private Long rechargemoney;

    private Date changetime;

    private String membername;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid == null ? null : memberid.trim();
    }

    public String getMembercardid() {
        return membercardid;
    }

    public void setMembercardid(String membercardid) {
        this.membercardid = membercardid == null ? null : membercardid.trim();
    }

    public Long getRechargemoney() {
        return rechargemoney;
    }

    public void setRechargemoney(Long rechargemoney) {
        this.rechargemoney = rechargemoney;
    }

    public Date getChangetime() {
        return changetime;
    }

    public void setChangetime(Date changetime) {
        this.changetime = changetime;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername == null ? null : membername.trim();
    }
}