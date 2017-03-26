package qlw.model;

public class Patient {
    private Long id;

    private Long uid;

    private String name;

    private Integer sex;

    private Integer type;

    private String phone;

    private String guardianname;

    private Integer guardianidtype;

    private String guardianidnumber;

    private String headpath;

    private Integer idtype;

    private String idnumber;

    private String birthday;

    private String createdate;

    private String deletedate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getGuardianname() {
        return guardianname;
    }

    public void setGuardianname(String guardianname) {
        this.guardianname = guardianname == null ? null : guardianname.trim();
    }

    public Integer getGuardianidtype() {
        return guardianidtype;
    }

    public void setGuardianidtype(Integer guardianidtype) {
        this.guardianidtype = guardianidtype;
    }

    public String getGuardianidnumber() {
        return guardianidnumber;
    }

    public void setGuardianidnumber(String guardianidnumber) {
        this.guardianidnumber = guardianidnumber == null ? null : guardianidnumber.trim();
    }

    public String getHeadpath() {
        return headpath;
    }

    public void setHeadpath(String headpath) {
        this.headpath = headpath == null ? null : headpath.trim();
    }

    public Integer getIdtype() {
        return idtype;
    }

    public void setIdtype(Integer idtype) {
        this.idtype = idtype;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber == null ? null : idnumber.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate == null ? null : createdate.trim();
    }

    public String getDeletedate() {
        return deletedate;
    }

    public void setDeletedate(String deletedate) {
        this.deletedate = deletedate == null ? null : deletedate.trim();
    }
}