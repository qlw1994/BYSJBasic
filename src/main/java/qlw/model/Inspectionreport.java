package qlw.model;

import java.util.List;

public class Inspectionreport {
    private Long id;

    private Long patientid;

    private String inspectname;

    private String inspecttime;

    private Long departmentid;

    private Long doctorid;

    private Long auditorid;

    private Long total;

    private String createdate;

    private String deletedate;

    private Long hospitalid;

    private String examtime;

    private Integer status;

    private String date;

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

    public String getInspectname() {
        return inspectname;
    }

    public void setInspectname(String inspectname) {
        this.inspectname = inspectname == null ? null : inspectname.trim();
    }

    public String getInspecttime() {
        return inspecttime;
    }

    public void setInspecttime(String inspecttime) {
        this.inspecttime = inspecttime == null ? null : inspecttime.trim();
    }

    public Long getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Long departmentid) {
        this.departmentid = departmentid;
    }

    public Long getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(Long doctorid) {
        this.doctorid = doctorid;
    }

    public Long getAuditorid() {
        return auditorid;
    }

    public void setAuditorid(Long auditorid) {
        this.auditorid = auditorid;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
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

    public Long getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(Long hospitalid) {
        this.hospitalid = hospitalid;
    }

    public String getExamtime() {
        return examtime;
    }

    public void setExamtime(String examtime) {
        this.examtime = examtime == null ? null : examtime.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }
    private List<Inspectitems> inspectitemss;

    private Patient patient;

    private Hospital hospital;

    private Department department;

    private Doctor doctor;

    private  Doctor auditor;

    public List<Inspectitems> getInspectitemss() {
        return inspectitemss;
    }

    public void setInspectitemss(List<Inspectitems> inspectitemss) {
        this.inspectitemss = inspectitemss;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Doctor getAuditor() {
        return auditor;
    }

    public void setAuditor(Doctor auditor) {
        this.auditor = auditor;
    }
}