package qlw.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SchedulingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SchedulingExample() {
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

        public Criteria andDoctoridIsNull() {
            addCriterion("doctorid is null");
            return (Criteria) this;
        }

        public Criteria andDoctoridIsNotNull() {
            addCriterion("doctorid is not null");
            return (Criteria) this;
        }

        public Criteria andDoctoridEqualTo(Long value) {
            addCriterion("doctorid =", value, "doctorid");
            return (Criteria) this;
        }

        public Criteria andDoctoridNotEqualTo(Long value) {
            addCriterion("doctorid <>", value, "doctorid");
            return (Criteria) this;
        }

        public Criteria andDoctoridGreaterThan(Long value) {
            addCriterion("doctorid >", value, "doctorid");
            return (Criteria) this;
        }

        public Criteria andDoctoridGreaterThanOrEqualTo(Long value) {
            addCriterion("doctorid >=", value, "doctorid");
            return (Criteria) this;
        }

        public Criteria andDoctoridLessThan(Long value) {
            addCriterion("doctorid <", value, "doctorid");
            return (Criteria) this;
        }

        public Criteria andDoctoridLessThanOrEqualTo(Long value) {
            addCriterion("doctorid <=", value, "doctorid");
            return (Criteria) this;
        }

        public Criteria andDoctoridIn(List<Long> values) {
            addCriterion("doctorid in", values, "doctorid");
            return (Criteria) this;
        }

        public Criteria andDoctoridNotIn(List<Long> values) {
            addCriterion("doctorid not in", values, "doctorid");
            return (Criteria) this;
        }

        public Criteria andDoctoridBetween(Long value1, Long value2) {
            addCriterion("doctorid between", value1, value2, "doctorid");
            return (Criteria) this;
        }

        public Criteria andDoctoridNotBetween(Long value1, Long value2) {
            addCriterion("doctorid not between", value1, value2, "doctorid");
            return (Criteria) this;
        }

        public Criteria andDateIsNull() {
            addCriterion("date is null");
            return (Criteria) this;
        }

        public Criteria andDateIsNotNull() {
            addCriterion("date is not null");
            return (Criteria) this;
        }

        public Criteria andDateEqualTo(String value) {
            addCriterion("date =", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(String value) {
            addCriterion("date <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(String value) {
            addCriterion("date >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(String value) {
            addCriterion("date >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThan(String value) {
            addCriterion("date <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(String value) {
            addCriterion("date <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLike(String value) {
            addCriterion("date like", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotLike(String value) {
            addCriterion("date not like", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateIn(List<String> values) {
            addCriterion("date in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotIn(List<String> values) {
            addCriterion("date not in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateBetween(String value1, String value2) {
            addCriterion("date between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotBetween(String value1, String value2) {
            addCriterion("date not between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andTimeflagIsNull() {
            addCriterion("timeflag is null");
            return (Criteria) this;
        }

        public Criteria andTimeflagIsNotNull() {
            addCriterion("timeflag is not null");
            return (Criteria) this;
        }

        public Criteria andTimeflagEqualTo(Integer value) {
            addCriterion("timeflag =", value, "timeflag");
            return (Criteria) this;
        }

        public Criteria andTimeflagNotEqualTo(Integer value) {
            addCriterion("timeflag <>", value, "timeflag");
            return (Criteria) this;
        }

        public Criteria andTimeflagGreaterThan(Integer value) {
            addCriterion("timeflag >", value, "timeflag");
            return (Criteria) this;
        }

        public Criteria andTimeflagGreaterThanOrEqualTo(Integer value) {
            addCriterion("timeflag >=", value, "timeflag");
            return (Criteria) this;
        }

        public Criteria andTimeflagLessThan(Integer value) {
            addCriterion("timeflag <", value, "timeflag");
            return (Criteria) this;
        }

        public Criteria andTimeflagLessThanOrEqualTo(Integer value) {
            addCriterion("timeflag <=", value, "timeflag");
            return (Criteria) this;
        }

        public Criteria andTimeflagIn(List<Integer> values) {
            addCriterion("timeflag in", values, "timeflag");
            return (Criteria) this;
        }

        public Criteria andTimeflagNotIn(List<Integer> values) {
            addCriterion("timeflag not in", values, "timeflag");
            return (Criteria) this;
        }

        public Criteria andTimeflagBetween(Integer value1, Integer value2) {
            addCriterion("timeflag between", value1, value2, "timeflag");
            return (Criteria) this;
        }

        public Criteria andTimeflagNotBetween(Integer value1, Integer value2) {
            addCriterion("timeflag not between", value1, value2, "timeflag");
            return (Criteria) this;
        }

        public Criteria andRegfeeIsNull() {
            addCriterion("regfee is null");
            return (Criteria) this;
        }

        public Criteria andRegfeeIsNotNull() {
            addCriterion("regfee is not null");
            return (Criteria) this;
        }

        public Criteria andRegfeeEqualTo(BigDecimal value) {
            addCriterion("regfee =", value, "regfee");
            return (Criteria) this;
        }

        public Criteria andRegfeeNotEqualTo(BigDecimal value) {
            addCriterion("regfee <>", value, "regfee");
            return (Criteria) this;
        }

        public Criteria andRegfeeGreaterThan(BigDecimal value) {
            addCriterion("regfee >", value, "regfee");
            return (Criteria) this;
        }

        public Criteria andRegfeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("regfee >=", value, "regfee");
            return (Criteria) this;
        }

        public Criteria andRegfeeLessThan(BigDecimal value) {
            addCriterion("regfee <", value, "regfee");
            return (Criteria) this;
        }

        public Criteria andRegfeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("regfee <=", value, "regfee");
            return (Criteria) this;
        }

        public Criteria andRegfeeIn(List<BigDecimal> values) {
            addCriterion("regfee in", values, "regfee");
            return (Criteria) this;
        }

        public Criteria andRegfeeNotIn(List<BigDecimal> values) {
            addCriterion("regfee not in", values, "regfee");
            return (Criteria) this;
        }

        public Criteria andRegfeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("regfee between", value1, value2, "regfee");
            return (Criteria) this;
        }

        public Criteria andRegfeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("regfee not between", value1, value2, "regfee");
            return (Criteria) this;
        }

        public Criteria andHospitalidIsNull() {
            addCriterion("hospitalid is null");
            return (Criteria) this;
        }

        public Criteria andHospitalidIsNotNull() {
            addCriterion("hospitalid is not null");
            return (Criteria) this;
        }

        public Criteria andHospitalidEqualTo(Long value) {
            addCriterion("hospitalid =", value, "hospitalid");
            return (Criteria) this;
        }

        public Criteria andHospitalidNotEqualTo(Long value) {
            addCriterion("hospitalid <>", value, "hospitalid");
            return (Criteria) this;
        }

        public Criteria andHospitalidGreaterThan(Long value) {
            addCriterion("hospitalid >", value, "hospitalid");
            return (Criteria) this;
        }

        public Criteria andHospitalidGreaterThanOrEqualTo(Long value) {
            addCriterion("hospitalid >=", value, "hospitalid");
            return (Criteria) this;
        }

        public Criteria andHospitalidLessThan(Long value) {
            addCriterion("hospitalid <", value, "hospitalid");
            return (Criteria) this;
        }

        public Criteria andHospitalidLessThanOrEqualTo(Long value) {
            addCriterion("hospitalid <=", value, "hospitalid");
            return (Criteria) this;
        }

        public Criteria andHospitalidIn(List<Long> values) {
            addCriterion("hospitalid in", values, "hospitalid");
            return (Criteria) this;
        }

        public Criteria andHospitalidNotIn(List<Long> values) {
            addCriterion("hospitalid not in", values, "hospitalid");
            return (Criteria) this;
        }

        public Criteria andHospitalidBetween(Long value1, Long value2) {
            addCriterion("hospitalid between", value1, value2, "hospitalid");
            return (Criteria) this;
        }

        public Criteria andHospitalidNotBetween(Long value1, Long value2) {
            addCriterion("hospitalid not between", value1, value2, "hospitalid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidIsNull() {
            addCriterion("departmentid is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentidIsNotNull() {
            addCriterion("departmentid is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentidEqualTo(Long value) {
            addCriterion("departmentid =", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidNotEqualTo(Long value) {
            addCriterion("departmentid <>", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidGreaterThan(Long value) {
            addCriterion("departmentid >", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidGreaterThanOrEqualTo(Long value) {
            addCriterion("departmentid >=", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidLessThan(Long value) {
            addCriterion("departmentid <", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidLessThanOrEqualTo(Long value) {
            addCriterion("departmentid <=", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidIn(List<Long> values) {
            addCriterion("departmentid in", values, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidNotIn(List<Long> values) {
            addCriterion("departmentid not in", values, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidBetween(Long value1, Long value2) {
            addCriterion("departmentid between", value1, value2, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidNotBetween(Long value1, Long value2) {
            addCriterion("departmentid not between", value1, value2, "departmentid");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTotalnumberIsNull() {
            addCriterion("totalnumber is null");
            return (Criteria) this;
        }

        public Criteria andTotalnumberIsNotNull() {
            addCriterion("totalnumber is not null");
            return (Criteria) this;
        }

        public Criteria andTotalnumberEqualTo(Integer value) {
            addCriterion("totalnumber =", value, "totalnumber");
            return (Criteria) this;
        }

        public Criteria andTotalnumberNotEqualTo(Integer value) {
            addCriterion("totalnumber <>", value, "totalnumber");
            return (Criteria) this;
        }

        public Criteria andTotalnumberGreaterThan(Integer value) {
            addCriterion("totalnumber >", value, "totalnumber");
            return (Criteria) this;
        }

        public Criteria andTotalnumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("totalnumber >=", value, "totalnumber");
            return (Criteria) this;
        }

        public Criteria andTotalnumberLessThan(Integer value) {
            addCriterion("totalnumber <", value, "totalnumber");
            return (Criteria) this;
        }

        public Criteria andTotalnumberLessThanOrEqualTo(Integer value) {
            addCriterion("totalnumber <=", value, "totalnumber");
            return (Criteria) this;
        }

        public Criteria andTotalnumberIn(List<Integer> values) {
            addCriterion("totalnumber in", values, "totalnumber");
            return (Criteria) this;
        }

        public Criteria andTotalnumberNotIn(List<Integer> values) {
            addCriterion("totalnumber not in", values, "totalnumber");
            return (Criteria) this;
        }

        public Criteria andTotalnumberBetween(Integer value1, Integer value2) {
            addCriterion("totalnumber between", value1, value2, "totalnumber");
            return (Criteria) this;
        }

        public Criteria andTotalnumberNotBetween(Integer value1, Integer value2) {
            addCriterion("totalnumber not between", value1, value2, "totalnumber");
            return (Criteria) this;
        }

        public Criteria andOtherleftcountIsNull() {
            addCriterion("otherleftcount is null");
            return (Criteria) this;
        }

        public Criteria andOtherleftcountIsNotNull() {
            addCriterion("otherleftcount is not null");
            return (Criteria) this;
        }

        public Criteria andOtherleftcountEqualTo(Integer value) {
            addCriterion("otherleftcount =", value, "otherleftcount");
            return (Criteria) this;
        }

        public Criteria andOtherleftcountNotEqualTo(Integer value) {
            addCriterion("otherleftcount <>", value, "otherleftcount");
            return (Criteria) this;
        }

        public Criteria andOtherleftcountGreaterThan(Integer value) {
            addCriterion("otherleftcount >", value, "otherleftcount");
            return (Criteria) this;
        }

        public Criteria andOtherleftcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("otherleftcount >=", value, "otherleftcount");
            return (Criteria) this;
        }

        public Criteria andOtherleftcountLessThan(Integer value) {
            addCriterion("otherleftcount <", value, "otherleftcount");
            return (Criteria) this;
        }

        public Criteria andOtherleftcountLessThanOrEqualTo(Integer value) {
            addCriterion("otherleftcount <=", value, "otherleftcount");
            return (Criteria) this;
        }

        public Criteria andOtherleftcountIn(List<Integer> values) {
            addCriterion("otherleftcount in", values, "otherleftcount");
            return (Criteria) this;
        }

        public Criteria andOtherleftcountNotIn(List<Integer> values) {
            addCriterion("otherleftcount not in", values, "otherleftcount");
            return (Criteria) this;
        }

        public Criteria andOtherleftcountBetween(Integer value1, Integer value2) {
            addCriterion("otherleftcount between", value1, value2, "otherleftcount");
            return (Criteria) this;
        }

        public Criteria andOtherleftcountNotBetween(Integer value1, Integer value2) {
            addCriterion("otherleftcount not between", value1, value2, "otherleftcount");
            return (Criteria) this;
        }

        public Criteria andAppointleftcountIsNull() {
            addCriterion("appointleftcount is null");
            return (Criteria) this;
        }

        public Criteria andAppointleftcountIsNotNull() {
            addCriterion("appointleftcount is not null");
            return (Criteria) this;
        }

        public Criteria andAppointleftcountEqualTo(Integer value) {
            addCriterion("appointleftcount =", value, "appointleftcount");
            return (Criteria) this;
        }

        public Criteria andAppointleftcountNotEqualTo(Integer value) {
            addCriterion("appointleftcount <>", value, "appointleftcount");
            return (Criteria) this;
        }

        public Criteria andAppointleftcountGreaterThan(Integer value) {
            addCriterion("appointleftcount >", value, "appointleftcount");
            return (Criteria) this;
        }

        public Criteria andAppointleftcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("appointleftcount >=", value, "appointleftcount");
            return (Criteria) this;
        }

        public Criteria andAppointleftcountLessThan(Integer value) {
            addCriterion("appointleftcount <", value, "appointleftcount");
            return (Criteria) this;
        }

        public Criteria andAppointleftcountLessThanOrEqualTo(Integer value) {
            addCriterion("appointleftcount <=", value, "appointleftcount");
            return (Criteria) this;
        }

        public Criteria andAppointleftcountIn(List<Integer> values) {
            addCriterion("appointleftcount in", values, "appointleftcount");
            return (Criteria) this;
        }

        public Criteria andAppointleftcountNotIn(List<Integer> values) {
            addCriterion("appointleftcount not in", values, "appointleftcount");
            return (Criteria) this;
        }

        public Criteria andAppointleftcountBetween(Integer value1, Integer value2) {
            addCriterion("appointleftcount between", value1, value2, "appointleftcount");
            return (Criteria) this;
        }

        public Criteria andAppointleftcountNotBetween(Integer value1, Integer value2) {
            addCriterion("appointleftcount not between", value1, value2, "appointleftcount");
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