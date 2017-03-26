package qlw.model;

import java.util.ArrayList;
import java.util.List;

public class InspectitemsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InspectitemsExample() {
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

        public Criteria andInspectionidIsNull() {
            addCriterion("inspectionid is null");
            return (Criteria) this;
        }

        public Criteria andInspectionidIsNotNull() {
            addCriterion("inspectionid is not null");
            return (Criteria) this;
        }

        public Criteria andInspectionidEqualTo(Long value) {
            addCriterion("inspectionid =", value, "inspectionid");
            return (Criteria) this;
        }

        public Criteria andInspectionidNotEqualTo(Long value) {
            addCriterion("inspectionid <>", value, "inspectionid");
            return (Criteria) this;
        }

        public Criteria andInspectionidGreaterThan(Long value) {
            addCriterion("inspectionid >", value, "inspectionid");
            return (Criteria) this;
        }

        public Criteria andInspectionidGreaterThanOrEqualTo(Long value) {
            addCriterion("inspectionid >=", value, "inspectionid");
            return (Criteria) this;
        }

        public Criteria andInspectionidLessThan(Long value) {
            addCriterion("inspectionid <", value, "inspectionid");
            return (Criteria) this;
        }

        public Criteria andInspectionidLessThanOrEqualTo(Long value) {
            addCriterion("inspectionid <=", value, "inspectionid");
            return (Criteria) this;
        }

        public Criteria andInspectionidIn(List<Long> values) {
            addCriterion("inspectionid in", values, "inspectionid");
            return (Criteria) this;
        }

        public Criteria andInspectionidNotIn(List<Long> values) {
            addCriterion("inspectionid not in", values, "inspectionid");
            return (Criteria) this;
        }

        public Criteria andInspectionidBetween(Long value1, Long value2) {
            addCriterion("inspectionid between", value1, value2, "inspectionid");
            return (Criteria) this;
        }

        public Criteria andInspectionidNotBetween(Long value1, Long value2) {
            addCriterion("inspectionid not between", value1, value2, "inspectionid");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("result is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("result is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(String value) {
            addCriterion("result =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(String value) {
            addCriterion("result <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(String value) {
            addCriterion("result >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(String value) {
            addCriterion("result >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(String value) {
            addCriterion("result <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(String value) {
            addCriterion("result <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLike(String value) {
            addCriterion("result like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotLike(String value) {
            addCriterion("result not like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<String> values) {
            addCriterion("result in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<String> values) {
            addCriterion("result not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(String value1, String value2) {
            addCriterion("result between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(String value1, String value2) {
            addCriterion("result not between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andRefrangeIsNull() {
            addCriterion("refrange is null");
            return (Criteria) this;
        }

        public Criteria andRefrangeIsNotNull() {
            addCriterion("refrange is not null");
            return (Criteria) this;
        }

        public Criteria andRefrangeEqualTo(String value) {
            addCriterion("refrange =", value, "refrange");
            return (Criteria) this;
        }

        public Criteria andRefrangeNotEqualTo(String value) {
            addCriterion("refrange <>", value, "refrange");
            return (Criteria) this;
        }

        public Criteria andRefrangeGreaterThan(String value) {
            addCriterion("refrange >", value, "refrange");
            return (Criteria) this;
        }

        public Criteria andRefrangeGreaterThanOrEqualTo(String value) {
            addCriterion("refrange >=", value, "refrange");
            return (Criteria) this;
        }

        public Criteria andRefrangeLessThan(String value) {
            addCriterion("refrange <", value, "refrange");
            return (Criteria) this;
        }

        public Criteria andRefrangeLessThanOrEqualTo(String value) {
            addCriterion("refrange <=", value, "refrange");
            return (Criteria) this;
        }

        public Criteria andRefrangeLike(String value) {
            addCriterion("refrange like", value, "refrange");
            return (Criteria) this;
        }

        public Criteria andRefrangeNotLike(String value) {
            addCriterion("refrange not like", value, "refrange");
            return (Criteria) this;
        }

        public Criteria andRefrangeIn(List<String> values) {
            addCriterion("refrange in", values, "refrange");
            return (Criteria) this;
        }

        public Criteria andRefrangeNotIn(List<String> values) {
            addCriterion("refrange not in", values, "refrange");
            return (Criteria) this;
        }

        public Criteria andRefrangeBetween(String value1, String value2) {
            addCriterion("refrange between", value1, value2, "refrange");
            return (Criteria) this;
        }

        public Criteria andRefrangeNotBetween(String value1, String value2) {
            addCriterion("refrange not between", value1, value2, "refrange");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andAbnormalIsNull() {
            addCriterion("abnormal is null");
            return (Criteria) this;
        }

        public Criteria andAbnormalIsNotNull() {
            addCriterion("abnormal is not null");
            return (Criteria) this;
        }

        public Criteria andAbnormalEqualTo(Integer value) {
            addCriterion("abnormal =", value, "abnormal");
            return (Criteria) this;
        }

        public Criteria andAbnormalNotEqualTo(Integer value) {
            addCriterion("abnormal <>", value, "abnormal");
            return (Criteria) this;
        }

        public Criteria andAbnormalGreaterThan(Integer value) {
            addCriterion("abnormal >", value, "abnormal");
            return (Criteria) this;
        }

        public Criteria andAbnormalGreaterThanOrEqualTo(Integer value) {
            addCriterion("abnormal >=", value, "abnormal");
            return (Criteria) this;
        }

        public Criteria andAbnormalLessThan(Integer value) {
            addCriterion("abnormal <", value, "abnormal");
            return (Criteria) this;
        }

        public Criteria andAbnormalLessThanOrEqualTo(Integer value) {
            addCriterion("abnormal <=", value, "abnormal");
            return (Criteria) this;
        }

        public Criteria andAbnormalIn(List<Integer> values) {
            addCriterion("abnormal in", values, "abnormal");
            return (Criteria) this;
        }

        public Criteria andAbnormalNotIn(List<Integer> values) {
            addCriterion("abnormal not in", values, "abnormal");
            return (Criteria) this;
        }

        public Criteria andAbnormalBetween(Integer value1, Integer value2) {
            addCriterion("abnormal between", value1, value2, "abnormal");
            return (Criteria) this;
        }

        public Criteria andAbnormalNotBetween(Integer value1, Integer value2) {
            addCriterion("abnormal not between", value1, value2, "abnormal");
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