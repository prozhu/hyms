package cn.itcast.ssm.model.consumerecord;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Consumerecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5510392696164510385L;

	private Long id;

    private String memberid;

    private String membercardid;

    private BigDecimal consumemoney;

    private String membername;

    private Date consumetime;

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

    public BigDecimal getConsumemoney() {
        return consumemoney;
    }

    public void setConsumemoney(BigDecimal consumemoney) {
        this.consumemoney = consumemoney;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername == null ? null : membername.trim();
    }

    public Date getConsumetime() {
        return consumetime;
    }

    public void setConsumetime(Date consumetime) {
        this.consumetime = consumetime;
    }
}