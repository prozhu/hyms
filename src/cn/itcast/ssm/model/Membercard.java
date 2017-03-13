package cn.itcast.ssm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Membercard implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8081177212374046059L;

	private Long id;

    private String memberid;

    private String membercardid;

    private Short cardstatus;

    private String cardgrade;

    private Date opendate;

    private Long discount;

    private BigDecimal balance;

    private Integer totalpoint;

    private BigDecimal totalconsumption;

    private Long totalrecharge;

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

    public Short getCardstatus() {
        return cardstatus;
    }

    public void setCardstatus(Short cardstatus) {
        this.cardstatus = cardstatus;
    }

    public String getCardgrade() {
        return cardgrade;
    }

    public void setCardgrade(String cardgrade) {
        this.cardgrade = cardgrade == null ? null : cardgrade.trim();
    }

    public Date getOpendate() {
        return opendate;
    }

    public void setOpendate(Date opendate) {
        this.opendate = opendate;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getTotalpoint() {
        return totalpoint;
    }

    public void setTotalpoint(Integer totalpoint) {
        this.totalpoint = totalpoint;
    }

    public BigDecimal getTotalconsumption() {
        return totalconsumption;
    }

    public void setTotalconsumption(BigDecimal totalconsumption) {
        this.totalconsumption = totalconsumption;
    }

    public Long getTotalrecharge() {
        return totalrecharge;
    }

    public void setTotalrecharge(Long totalrecharge) {
        this.totalrecharge = totalrecharge;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername == null ? null : membername.trim();
    }
}