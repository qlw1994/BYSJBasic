package qlw.model;

public class Numbers {
    private Long id;

    private String date;

    private Integer appointleftcount;

    private Integer otherleftcount;

    private Integer type;

    private String starttime;

    private String endtime;

    private Long hospitalid;

    private Long departmentid;

    private Integer totalamount;

    private Long schedulingid;

    private Integer status;

    private Integer timeflag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public Integer getAppointleftcount() {
        return appointleftcount;
    }

    public void setAppointleftcount(Integer appointleftcount) {
        this.appointleftcount = appointleftcount;
    }

    public Integer getOtherleftcount() {
        return otherleftcount;
    }

    public void setOtherleftcount(Integer otherleftcount) {
        this.otherleftcount = otherleftcount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime == null ? null : starttime.trim();
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime == null ? null : endtime.trim();
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

    public Integer getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(Integer totalamount) {
        this.totalamount = totalamount;
    }

    public Long getSchedulingid() {
        return schedulingid;
    }

    public void setSchedulingid(Long schedulingid) {
        this.schedulingid = schedulingid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTimeflag() {
        return timeflag;
    }

    public void setTimeflag(Integer timeflag) {
        this.timeflag = timeflag;
    }
}