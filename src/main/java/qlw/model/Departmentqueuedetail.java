package qlw.model;

public class Departmentqueuedetail {
    private Long id;

    private Long departmentqueueid;

    private Long patientid;

    private String patientname;

    private Integer number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartmentqueueid() {
        return departmentqueueid;
    }

    public void setDepartmentqueueid(Long departmentqueueid) {
        this.departmentqueueid = departmentqueueid;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    private Departmentqueue departmentqueue;
    private Hospital hospital;
    private Patient patient;
    private Department department;

    public Departmentqueue getDepartmentqueue() {
        return departmentqueue;
    }

    public void setDepartmentqueue(Departmentqueue departmentqueue) {
        this.departmentqueue = departmentqueue;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}