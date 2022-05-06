package cn.prozhu.ssm.model.membercard;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MemberCard implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8081177212374046059L;

	private Long id;

    private String memberId;

    private String memberCardId;

    private Short cardStatus;

    private String cardGrade;

    private Date openDate;

    private double discount;

    private BigDecimal balance;

    private Integer totalPoint;

    private BigDecimal totalConsumption;

    private Long totalRecharge;

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

    public Short getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Short cardstatus) {
        this.cardStatus = cardstatus;
    }

    public String getCardGrade() {
        return cardGrade;
    }

    public void setCardGrade(String cardgrade) {
        this.cardGrade = cardgrade == null ? null : cardgrade.trim();
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date opendate) {
        this.openDate = opendate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalpoint) {
        this.totalPoint = totalpoint;
    }

    public BigDecimal getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(BigDecimal totalconsumption) {
        this.totalConsumption = totalconsumption;
    }

    public Long getTotalRecharge() {
        return totalRecharge;
    }

    public void setTotalRecharge(Long totalrecharge) {
        this.totalRecharge = totalrecharge;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String membername) {
        this.memberName = membername == null ? null : membername.trim();
    }
}