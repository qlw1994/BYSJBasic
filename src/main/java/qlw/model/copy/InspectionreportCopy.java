package qlw.model.copy;

import qlw.model.*;

import java.util.List;

/**
 * Created by wiki on 2017/3/18.
 */
public class InspectionreportCopy {
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
