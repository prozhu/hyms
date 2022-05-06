package cn.prozhu.ssm.model.membercard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberCardExample implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6896540984751280353L;

	protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MemberCardExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andMemberidIsNull() {
            addCriterion("memberId is null");
            return (Criteria) this;
        }

        public Criteria andMemberidIsNotNull() {
            addCriterion("memberId is not null");
            return (Criteria) this;
        }

        public Criteria andMemberidEqualTo(String value) {
            addCriterion("memberId =", value, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidNotEqualTo(String value) {
            addCriterion("memberId <>", value, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidGreaterThan(String value) {
            addCriterion("memberId >", value, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidGreaterThanOrEqualTo(String value) {
            addCriterion("memberId >=", value, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidLessThan(String value) {
            addCriterion("memberId <", value, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidLessThanOrEqualTo(String value) {
            addCriterion("memberId <=", value, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidLike(String value) {
            addCriterion("memberId like", value, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidNotLike(String value) {
            addCriterion("memberId not like", value, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidIn(List<String> values) {
            addCriterion("memberId in", values, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidNotIn(List<String> values) {
            addCriterion("memberId not in", values, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidBetween(String value1, String value2) {
            addCriterion("memberId between", value1, value2, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidNotBetween(String value1, String value2) {
            addCriterion("memberId not between", value1, value2, "memberid");
            return (Criteria) this;
        }

        public Criteria andMembercardidIsNull() {
            addCriterion("memberCardId is null");
            return (Criteria) this;
        }

        public Criteria andMembercardidIsNotNull() {
            addCriterion("memberCardId is not null");
            return (Criteria) this;
        }

        public Criteria andMembercardidEqualTo(String value) {
            addCriterion("memberCardId =", value, "membercardid");
            return (Criteria) this;
        }

        public Criteria andMembercardidNotEqualTo(String value) {
            addCriterion("memberCardId <>", value, "membercardid");
            return (Criteria) this;
        }

        public Criteria andMembercardidGreaterThan(String value) {
            addCriterion("memberCardId >", value, "membercardid");
            return (Criteria) this;
        }

        public Criteria andMembercardidGreaterThanOrEqualTo(String value) {
            addCriterion("memberCardId >=", value, "membercardid");
            return (Criteria) this;
        }

        public Criteria andMembercardidLessThan(String value) {
            addCriterion("memberCardId <", value, "membercardid");
            return (Criteria) this;
        }

        public Criteria andMembercardidLessThanOrEqualTo(String value) {
            addCriterion("memberCardId <=", value, "membercardid");
            return (Criteria) this;
        }

        public Criteria andMembercardidLike(String value) {
            addCriterion("memberCardId like", value, "membercardid");
            return (Criteria) this;
        }

        public Criteria andMembercardidNotLike(String value) {
            addCriterion("memberCardId not like", value, "membercardid");
            return (Criteria) this;
        }

        public Criteria andMembercardidIn(List<String> values) {
            addCriterion("memberCardId in", values, "membercardid");
            return (Criteria) this;
        }

        public Criteria andMembercardidNotIn(List<String> values) {
            addCriterion("memberCardId not in", values, "membercardid");
            return (Criteria) this;
        }

        public Criteria andMembercardidBetween(String value1, String value2) {
            addCriterion("memberCardId between", value1, value2, "membercardid");
            return (Criteria) this;
        }

        public Criteria andMembercardidNotBetween(String value1, String value2) {
            addCriterion("memberCardId not between", value1, value2, "membercardid");
            return (Criteria) this;
        }

        public Criteria andCardstatusIsNull() {
            addCriterion("cardStatus is null");
            return (Criteria) this;
        }

        public Criteria andCardstatusIsNotNull() {
            addCriterion("cardStatus is not null");
            return (Criteria) this;
        }

        public Criteria andCardstatusEqualTo(Short value) {
            addCriterion("cardStatus =", value, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusNotEqualTo(Short value) {
            addCriterion("cardStatus <>", value, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusGreaterThan(Short value) {
            addCriterion("cardStatus >", value, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusGreaterThanOrEqualTo(Short value) {
            addCriterion("cardStatus >=", value, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusLessThan(Short value) {
            addCriterion("cardStatus <", value, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusLessThanOrEqualTo(Short value) {
            addCriterion("cardStatus <=", value, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusIn(List<Short> values) {
            addCriterion("cardStatus in", values, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusNotIn(List<Short> values) {
            addCriterion("cardStatus not in", values, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusBetween(Short value1, Short value2) {
            addCriterion("cardStatus between", value1, value2, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusNotBetween(Short value1, Short value2) {
            addCriterion("cardStatus not between", value1, value2, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardgradeIsNull() {
            addCriterion("cardGrade is null");
            return (Criteria) this;
        }

        public Criteria andCardgradeIsNotNull() {
            addCriterion("cardGrade is not null");
            return (Criteria) this;
        }

        public Criteria andCardgradeEqualTo(String value) {
            addCriterion("cardGrade =", value, "cardgrade");
            return (Criteria) this;
        }

        public Criteria andCardgradeNotEqualTo(String value) {
            addCriterion("cardGrade <>", value, "cardgrade");
            return (Criteria) this;
        }

        public Criteria andCardgradeGreaterThan(String value) {
            addCriterion("cardGrade >", value, "cardgrade");
            return (Criteria) this;
        }

        public Criteria andCardgradeGreaterThanOrEqualTo(String value) {
            addCriterion("cardGrade >=", value, "cardgrade");
            return (Criteria) this;
        }

        public Criteria andCardgradeLessThan(String value) {
            addCriterion("cardGrade <", value, "cardgrade");
            return (Criteria) this;
        }

        public Criteria andCardgradeLessThanOrEqualTo(String value) {
            addCriterion("cardGrade <=", value, "cardgrade");
            return (Criteria) this;
        }

        public Criteria andCardgradeLike(String value) {
            addCriterion("cardGrade like", value, "cardgrade");
            return (Criteria) this;
        }

        public Criteria andCardgradeNotLike(String value) {
            addCriterion("cardGrade not like", value, "cardgrade");
            return (Criteria) this;
        }

        public Criteria andCardgradeIn(List<String> values) {
            addCriterion("cardGrade in", values, "cardgrade");
            return (Criteria) this;
        }

        public Criteria andCardgradeNotIn(List<String> values) {
            addCriterion("cardGrade not in", values, "cardgrade");
            return (Criteria) this;
        }

        public Criteria andCardgradeBetween(String value1, String value2) {
            addCriterion("cardGrade between", value1, value2, "cardgrade");
            return (Criteria) this;
        }

        public Criteria andCardgradeNotBetween(String value1, String value2) {
            addCriterion("cardGrade not between", value1, value2, "cardgrade");
            return (Criteria) this;
        }

        public Criteria andOpendateIsNull() {
            addCriterion("openDate is null");
            return (Criteria) this;
        }

        public Criteria andOpendateIsNotNull() {
            addCriterion("openDate is not null");
            return (Criteria) this;
        }

        public Criteria andOpendateEqualTo(Date value) {
            addCriterion("openDate =", value, "opendate");
            return (Criteria) this;
        }

        public Criteria andOpendateNotEqualTo(Date value) {
            addCriterion("openDate <>", value, "opendate");
            return (Criteria) this;
        }

        public Criteria andOpendateGreaterThan(Date value) {
            addCriterion("openDate >", value, "opendate");
            return (Criteria) this;
        }

        public Criteria andOpendateGreaterThanOrEqualTo(Date value) {
            addCriterion("openDate >=", value, "opendate");
            return (Criteria) this;
        }

        public Criteria andOpendateLessThan(Date value) {
            addCriterion("openDate <", value, "opendate");
            return (Criteria) this;
        }

        public Criteria andOpendateLessThanOrEqualTo(Date value) {
            addCriterion("openDate <=", value, "opendate");
            return (Criteria) this;
        }

        public Criteria andOpendateIn(List<Date> values) {
            addCriterion("openDate in", values, "opendate");
            return (Criteria) this;
        }

        public Criteria andOpendateNotIn(List<Date> values) {
            addCriterion("openDate not in", values, "opendate");
            return (Criteria) this;
        }

        public Criteria andOpendateBetween(Date value1, Date value2) {
            addCriterion("openDate between", value1, value2, "opendate");
            return (Criteria) this;
        }

        public Criteria andOpendateNotBetween(Date value1, Date value2) {
            addCriterion("openDate not between", value1, value2, "opendate");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNull() {
            addCriterion("discount is null");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNotNull() {
            addCriterion("discount is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountEqualTo(Long value) {
            addCriterion("discount =", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotEqualTo(Long value) {
            addCriterion("discount <>", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThan(Long value) {
            addCriterion("discount >", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThanOrEqualTo(Long value) {
            addCriterion("discount >=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThan(Long value) {
            addCriterion("discount <", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThanOrEqualTo(Long value) {
            addCriterion("discount <=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountIn(List<Long> values) {
            addCriterion("discount in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotIn(List<Long> values) {
            addCriterion("discount not in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountBetween(Long value1, Long value2) {
            addCriterion("discount between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotBetween(Long value1, Long value2) {
            addCriterion("discount not between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNull() {
            addCriterion("balance is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNotNull() {
            addCriterion("balance is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceEqualTo(Long value) {
            addCriterion("balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(Long value) {
            addCriterion("balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(Long value) {
            addCriterion("balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(Long value) {
            addCriterion("balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(Long value) {
            addCriterion("balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(Long value) {
            addCriterion("balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<Long> values) {
            addCriterion("balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<Long> values) {
            addCriterion("balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(Long value1, Long value2) {
            addCriterion("balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(Long value1, Long value2) {
            addCriterion("balance not between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andTotalpointIsNull() {
            addCriterion("totalPoint is null");
            return (Criteria) this;
        }

        public Criteria andTotalpointIsNotNull() {
            addCriterion("totalPoint is not null");
            return (Criteria) this;
        }

        public Criteria andTotalpointEqualTo(Integer value) {
            addCriterion("totalPoint =", value, "totalpoint");
            return (Criteria) this;
        }

        public Criteria andTotalpointNotEqualTo(Integer value) {
            addCriterion("totalPoint <>", value, "totalpoint");
            return (Criteria) this;
        }

        public Criteria andTotalpointGreaterThan(Integer value) {
            addCriterion("totalPoint >", value, "totalpoint");
            return (Criteria) this;
        }

        public Criteria andTotalpointGreaterThanOrEqualTo(Integer value) {
            addCriterion("totalPoint >=", value, "totalpoint");
            return (Criteria) this;
        }

        public Criteria andTotalpointLessThan(Integer value) {
            addCriterion("totalPoint <", value, "totalpoint");
            return (Criteria) this;
        }

        public Criteria andTotalpointLessThanOrEqualTo(Integer value) {
            addCriterion("totalPoint <=", value, "totalpoint");
            return (Criteria) this;
        }

        public Criteria andTotalpointIn(List<Integer> values) {
            addCriterion("totalPoint in", values, "totalpoint");
            return (Criteria) this;
        }

        public Criteria andTotalpointNotIn(List<Integer> values) {
            addCriterion("totalPoint not in", values, "totalpoint");
            return (Criteria) this;
        }

        public Criteria andTotalpointBetween(Integer value1, Integer value2) {
            addCriterion("totalPoint between", value1, value2, "totalpoint");
            return (Criteria) this;
        }

        public Criteria andTotalpointNotBetween(Integer value1, Integer value2) {
            addCriterion("totalPoint not between", value1, value2, "totalpoint");
            return (Criteria) this;
        }

        public Criteria andTotalconsumptionIsNull() {
            addCriterion("totalConsumption is null");
            return (Criteria) this;
        }

        public Criteria andTotalconsumptionIsNotNull() {
            addCriterion("totalConsumption is not null");
            return (Criteria) this;
        }

        public Criteria andTotalconsumptionEqualTo(Long value) {
            addCriterion("totalConsumption =", value, "totalconsumption");
            return (Criteria) this;
        }

        public Criteria andTotalconsumptionNotEqualTo(Long value) {
            addCriterion("totalConsumption <>", value, "totalconsumption");
            return (Criteria) this;
        }

        public Criteria andTotalconsumptionGreaterThan(Long value) {
            addCriterion("totalConsumption >", value, "totalconsumption");
            return (Criteria) this;
        }

        public Criteria andTotalconsumptionGreaterThanOrEqualTo(Long value) {
            addCriterion("totalConsumption >=", value, "totalconsumption");
            return (Criteria) this;
        }

        public Criteria andTotalconsumptionLessThan(Long value) {
            addCriterion("totalConsumption <", value, "totalconsumption");
            return (Criteria) this;
        }

        public Criteria andTotalconsumptionLessThanOrEqualTo(Long value) {
            addCriterion("totalConsumption <=", value, "totalconsumption");
            return (Criteria) this;
        }

        public Criteria andTotalconsumptionIn(List<Long> values) {
            addCriterion("totalConsumption in", values, "totalconsumption");
            return (Criteria) this;
        }

        public Criteria andTotalconsumptionNotIn(List<Long> values) {
            addCriterion("totalConsumption not in", values, "totalconsumption");
            return (Criteria) this;
        }

        public Criteria andTotalconsumptionBetween(Long value1, Long value2) {
            addCriterion("totalConsumption between", value1, value2, "totalconsumption");
            return (Criteria) this;
        }

        public Criteria andTotalconsumptionNotBetween(Long value1, Long value2) {
            addCriterion("totalConsumption not between", value1, value2, "totalconsumption");
            return (Criteria) this;
        }

        public Criteria andTotalrechargeIsNull() {
            addCriterion("totalRecharge is null");
            return (Criteria) this;
        }

        public Criteria andTotalrechargeIsNotNull() {
            addCriterion("totalRecharge is not null");
            return (Criteria) this;
        }

        public Criteria andTotalrechargeEqualTo(Long value) {
            addCriterion("totalRecharge =", value, "totalrecharge");
            return (Criteria) this;
        }

        public Criteria andTotalrechargeNotEqualTo(Long value) {
            addCriterion("totalRecharge <>", value, "totalrecharge");
            return (Criteria) this;
        }

        public Criteria andTotalrechargeGreaterThan(Long value) {
            addCriterion("totalRecharge >", value, "totalrecharge");
            return (Criteria) this;
        }

        public Criteria andTotalrechargeGreaterThanOrEqualTo(Long value) {
            addCriterion("totalRecharge >=", value, "totalrecharge");
            return (Criteria) this;
        }

        public Criteria andTotalrechargeLessThan(Long value) {
            addCriterion("totalRecharge <", value, "totalrecharge");
            return (Criteria) this;
        }

        public Criteria andTotalrechargeLessThanOrEqualTo(Long value) {
            addCriterion("totalRecharge <=", value, "totalrecharge");
            return (Criteria) this;
        }

        public Criteria andTotalrechargeIn(List<Long> values) {
            addCriterion("totalRecharge in", values, "totalrecharge");
            return (Criteria) this;
        }

        public Criteria andTotalrechargeNotIn(List<Long> values) {
            addCriterion("totalRecharge not in", values, "totalrecharge");
            return (Criteria) this;
        }

        public Criteria andTotalrechargeBetween(Long value1, Long value2) {
            addCriterion("totalRecharge between", value1, value2, "totalrecharge");
            return (Criteria) this;
        }

        public Criteria andTotalrechargeNotBetween(Long value1, Long value2) {
            addCriterion("totalRecharge not between", value1, value2, "totalrecharge");
            return (Criteria) this;
        }

        public Criteria andMembernameIsNull() {
            addCriterion("memberName is null");
            return (Criteria) this;
        }

        public Criteria andMembernameIsNotNull() {
            addCriterion("memberName is not null");
            return (Criteria) this;
        }

        public Criteria andMembernameEqualTo(String value) {
            addCriterion("memberName =", value, "membername");
            return (Criteria) this;
        }

        public Criteria andMembernameNotEqualTo(String value) {
            addCriterion("memberName <>", value, "membername");
            return (Criteria) this;
        }

        public Criteria andMembernameGreaterThan(String value) {
            addCriterion("memberName >", value, "membername");
            return (Criteria) this;
        }

        public Criteria andMembernameGreaterThanOrEqualTo(String value) {
            addCriterion("memberName >=", value, "membername");
            return (Criteria) this;
        }

        public Criteria andMembernameLessThan(String value) {
            addCriterion("memberName <", value, "membername");
            return (Criteria) this;
        }

        public Criteria andMembernameLessThanOrEqualTo(String value) {
            addCriterion("memberName <=", value, "membername");
            return (Criteria) this;
        }

        public Criteria andMembernameLike(String value) {
            addCriterion("memberName like", value, "membername");
            return (Criteria) this;
        }

        public Criteria andMembernameNotLike(String value) {
            addCriterion("memberName not like", value, "membername");
            return (Criteria) this;
        }

        public Criteria andMembernameIn(List<String> values) {
            addCriterion("memberName in", values, "membername");
            return (Criteria) this;
        }

        public Criteria andMembernameNotIn(List<String> values) {
            addCriterion("memberName not in", values, "membername");
            return (Criteria) this;
        }

        public Criteria andMembernameBetween(String value1, String value2) {
            addCriterion("memberName between", value1, value2, "membername");
            return (Criteria) this;
        }

        public Criteria andMembernameNotBetween(String value1, String value2) {
            addCriterion("memberName not between", value1, value2, "membername");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}