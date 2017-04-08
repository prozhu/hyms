package cn.prozhu.ssm.model.consumerecord;

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

    private BigDecimal discountMoney;//打折之前的金额
    private BigDecimal consumeMoney; //打折之后的金额

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


    public BigDecimal getConsumeMoney() {
		return consumeMoney;
	}

	public void setConsumeMoney(BigDecimal consumeMoney) {
		this.consumeMoney = consumeMoney;
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

	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}
    
}