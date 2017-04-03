package qlw.model;

import java.math.BigDecimal;
import java.util.List;

public class Drugorder {
    private Long id;

    private Long doctorid;

    private Long patientid;

    private String createdate;

    private BigDecimal money;

    private Integer status;

    private String advice;

    private Integer total;

    private String deletedate;

    private Long hospitalid;

    private Long departmentid;

    private Integer needpay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(Long doctorid) {
        this.doctorid = doctorid;
    }

    public Long getPatientid() {
        return patientid;
    }

    public void setPatientid(Long patientid) {
        this.patientid = patientid;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate == null ? null : createdate.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice == null ? null : advice.trim();
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getDeletedate() {
        return deletedate;
    }

    public void setDeletedate(String deletedate) {
        this.deletedate = deletedate == null ? null : deletedate.trim();
    }

    public Long getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(Long hospitalid) {
        this.hospitalid = hospitalid;
    }

    public Long getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Long departmentid) {
        this.departmentid = departmentid;
    }

    public Integer getNeedpay() {
        return needpay;
    }

    public void setNeedpay(Integer needpay) {
        this.needpay = needpay;
    }
    Hospital hospital;
    Department department;
    List<Drugorderdetail> drugorderdetails;

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Drugorderdetail> getDrugorderdetails() {
        return drugorderdetails;
    }

    public void setDrugorderdetails(List<Drugorderdetail> drugorderdetails) {
        this.drugorderdetails = drugorderdetails;
    }
}