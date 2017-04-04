package cn.itcast.ssm.model.discount;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Discount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -35495879420842553L;
	private Integer id; // 主键自增id
	private BigDecimal lowConsume; // 消费区间（低）
	private BigDecimal highConsume; // 消费区间(高)
	private String grade; // 会员卡级别
	private double discount; // 折扣
	private Date createTime; // 创建时间
	private boolean delFlag;// 删除标记
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getLowConsume() {
		return lowConsume;
	}
	public void setLowConsume(BigDecimal lowConsume) {
		this.lowConsume = lowConsume;
	}
	public BigDecimal getHighConsume() {
		return highConsume;
	}
	public void setHighConsume(BigDecimal highConsume) {
		this.highConsume = highConsume;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public boolean isDelFlag() {
		return delFlag;
	}
	public void setDelFlag(boolean delFlag) {
		this.delFlag = delFlag;
	}
	@Override
	public String toString() {
		return "Discount [id=" + id + ", lowConsume=" + lowConsume
				+ ", highConsume=" + highConsume + ", grade=" + grade
				+ ", discount=" + discount + ", createTime=" + createTime
				+ ", delFlag=" + delFlag + "]";
	}

	
}
