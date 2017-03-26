package qlw.model;

import java.util.List;

public class Departmentqueue {
    private Long id;

    private String departmentname;

    private Long departmentid;

    private String hospitalname;

    private Long hospitalid;

    private Integer nowcount;

    private Integer avgtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname == null ? null : departmentname.trim();
    }

    public Long getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Long departmentid) {
        this.departmentid = departmentid;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname == null ? null : hospitalname.trim();
    }

    public Long getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(Long hospitalid) {
        this.hospitalid = hospitalid;
    }

    public Integer getNowcount() {
        return nowcount;
    }

    public void setNowcount(Integer nowcount) {
        this.nowcount = nowcount;
    }

    public Integer getAvgtime() {
        return avgtime;
    }

    public void setAvgtime(Integer avgtime) {
        this.avgtime = avgtime;
    }

    private List<Departmentqueuedetail> departmentqueuedetails;

    public List<Departmentqueuedetail> getDepartmentqueuedetails() {
        return departmentqueuedetails;
    }

    public void setDepartmentqueuedetails(List<Departmentqueuedetail> departmentqueuedetails) {
        this.departmentqueuedetails = departmentqueuedetails;
    }
}