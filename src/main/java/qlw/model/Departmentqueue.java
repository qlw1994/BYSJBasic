package qlw.model;

public class Departmentqueue {
    private Long id;

    private String departmentname;

    private Long departmentid;

    private String hospitalname;

    private Long hospitalid;

    private Integer nowtotal;

    private Integer avgtime;

    private Integer todaytotal;

    private Integer nownumber;

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

    public Integer getNowtotal() {
        return nowtotal;
    }

    public void setNowtotal(Integer nowtotal) {
        this.nowtotal = nowtotal;
    }

    public Integer getAvgtime() {
        return avgtime;
    }

    public void setAvgtime(Integer avgtime) {
        this.avgtime = avgtime;
    }

    public Integer getTodaytotal() {
        return todaytotal;
    }

    public void setTodaytotal(Integer todaytotal) {
        this.todaytotal = todaytotal;
    }

    public Integer getNownumber() {
        return nownumber;
    }

    public void setNownumber(Integer nownumber) {
        this.nownumber = nownumber;
    }
}