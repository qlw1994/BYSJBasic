package qlw.model.copy;

import qlw.model.Department;
import qlw.model.Doctor;
import qlw.model.Hospital;

/**
 * Created by wiki on 2017/3/20.
 */
public class FixedschedulingCopy {
    private Doctor doctor;
    private Hospital hospital;
    private Department department;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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
}
