package cn.itcast.ssm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CardrechargerecordExample implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3315748419038801941L;

	protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CardrechargerecordExample() {
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

        public Criteria andRechargemoneyIsNull() {
            addCriterion("rechargeMoney is null");
            return (Criteria) this;
        }

        public Criteria andRechargemoneyIsNotNull() {
            addCriterion("rechargeMoney is not null");
            return (Criteria) this;
        }

        public Criteria andRechargemoneyEqualTo(Long value) {
            addCriterion("rechargeMoney =", value, "rechargemoney");
            return (Criteria) this;
        }

        public Criteria andRechargemoneyNotEqualTo(Long value) {
            addCriterion("rechargeMoney <>", value, "rechargemoney");
            return (Criteria) this;
        }

        public Criteria andRechargemoneyGreaterThan(Long value) {
            addCriterion("rechargeMoney >", value, "rechargemoney");
            return (Criteria) this;
        }

        public Criteria andRechargemoneyGreaterThanOrEqualTo(Long value) {
            addCriterion("rechargeMoney >=", value, "rechargemoney");
            return (Criteria) this;
        }

        public Criteria andRechargemoneyLessThan(Long value) {
            addCriterion("rechargeMoney <", value, "rechargemoney");
            return (Criteria) this;
        }

        public Criteria andRechargemoneyLessThanOrEqualTo(Long value) {
            addCriterion("rechargeMoney <=", value, "rechargemoney");
            return (Criteria) this;
        }

        public Criteria andRechargemoneyIn(List<Long> values) {
            addCriterion("rechargeMoney in", values, "rechargemoney");
            return (Criteria) this;
        }

        public Criteria andRechargemoneyNotIn(List<Long> values) {
            addCriterion("rechargeMoney not in", values, "rechargemoney");
            return (Criteria) this;
        }

        public Criteria andRechargemoneyBetween(Long value1, Long value2) {
            addCriterion("rechargeMoney between", value1, value2, "rechargemoney");
            return (Criteria) this;
        }

        public Criteria andRechargemoneyNotBetween(Long value1, Long value2) {
            addCriterion("rechargeMoney not between", value1, value2, "rechargemoney");
            return (Criteria) this;
        }

        public Criteria andChangetimeIsNull() {
            addCriterion("changetime is null");
            return (Criteria) this;
        }

        public Criteria andChangetimeIsNotNull() {
            addCriterion("changetime is not null");
            return (Criteria) this;
        }

        public Criteria andChangetimeEqualTo(Date value) {
            addCriterion("changetime =", value, "changetime");
            return (Criteria) this;
        }

        public Criteria andChangetimeNotEqualTo(Date value) {
            addCriterion("changetime <>", value, "changetime");
            return (Criteria) this;
        }

        public Criteria andChangetimeGreaterThan(Date value) {
            addCriterion("changetime >", value, "changetime");
            return (Criteria) this;
        }

        public Criteria andChangetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("changetime >=", value, "changetime");
            return (Criteria) this;
        }

        public Criteria andChangetimeLessThan(Date value) {
            addCriterion("changetime <", value, "changetime");
            return (Criteria) this;
        }

        public Criteria andChangetimeLessThanOrEqualTo(Date value) {
            addCriterion("changetime <=", value, "changetime");
            return (Criteria) this;
        }

        public Criteria andChangetimeIn(List<Date> values) {
            addCriterion("changetime in", values, "changetime");
            return (Criteria) this;
        }

        public Criteria andChangetimeNotIn(List<Date> values) {
            addCriterion("changetime not in", values, "changetime");
            return (Criteria) this;
        }

        public Criteria andChangetimeBetween(Date value1, Date value2) {
            addCriterion("changetime between", value1, value2, "changetime");
            return (Criteria) this;
        }

        public Criteria andChangetimeNotBetween(Date value1, Date value2) {
            addCriterion("changetime not between", value1, value2, "changetime");
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