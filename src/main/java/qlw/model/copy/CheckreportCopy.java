package qlw.model.copy;

import qlw.model.Department;
import qlw.model.Doctor;
import qlw.model.Hospital;
import qlw.model.Patient;

/**
 * Created by wiki on 2017/3/18.
 */
public class CheckreportCopy {
    Patient patient;
    Hospital hospital;
    Department department;
    Doctor doctor;
    Doctor auditor;

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
