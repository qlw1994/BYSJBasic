package qlw.model;

import java.math.BigDecimal;
import java.util.List;

public class Hospitalization {
    private Long id;

    private Long patientid;

    private String patientname;

    private Integer bednumber;

    private String startdate;

    private String enddate;

    private Long departmentid;

    private String departmentname;

    private BigDecimal money;

    private Integer status;

    private Long hospitalid;

    private String hospitalname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientid() {
        return patientid;
    }

    public void setPatientid(Long patientid) {
        this.patientid = patientid;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname == null ? null : patientname.trim();
    }

    public Integer getBednumber() {
        return bednumber;
    }

    public void setBednumber(Integer bednumber) {
        this.bednumber = bednumber;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate == null ? null : startdate.trim();
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }

    public Long getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Long departmentid) {
        this.departmentid = departmentid;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname == null ? null : departmentname.trim();
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

    public Long getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(Long hospitalid) {
        this.hospitalid = hospitalid;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname == null ? null : hospitalname.trim();
    }
    public List<Hospitalpay> getHospitalpays() {
        return hospitalpays;
    }

    public void setHospitalpays(List<Hospitalpay> hospitalpays) {
        this.hospitalpays = hospitalpays;
    }

    private List<Hospitalpay> hospitalpays;

}