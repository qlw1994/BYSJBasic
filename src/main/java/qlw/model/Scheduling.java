package qlw.model;

import java.math.BigDecimal;

public class Scheduling {
    private Long id;

    private Long doctorid;

    private String date;

    private Integer timeflag;

    private BigDecimal regfee;

    private Long hospitalid;

    private Long departmentid;

    private Integer status;

    private Integer type;

    private Integer totalnumber;

    private Integer otherleftcount;

    private Integer appointleftcount;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public Integer getTimeflag() {
        return timeflag;
    }

    public void setTimeflag(Integer timeflag) {
        this.timeflag = timeflag;
    }

    public BigDecimal getRegfee() {
        return regfee;
    }

    public void setRegfee(BigDecimal regfee) {
        this.regfee = regfee;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTotalnumber() {
        return totalnumber;
    }

    public void setTotalnumber(Integer totalnumber) {
        this.totalnumber = totalnumber;
    }

    public Integer getOtherleftcount() {
        return otherleftcount;
    }

    public void setOtherleftcount(Integer otherleftcount) {
        this.otherleftcount = otherleftcount;
    }

    public Integer getAppointleftcount() {
        return appointleftcount;
    }

    public void setAppointleftcount(Integer appointleftcount) {
        this.appointleftcount = appointleftcount;
    }
}