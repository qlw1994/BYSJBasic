package qlw.model.copy;

import qlw.model.Department;
import qlw.model.Departmentqueue;
import qlw.model.Hospital;
import qlw.model.Patient;

/**
 * Created by wiki on 2017/3/22.
 */
public class DepartmentqueuedetailCopy {
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
