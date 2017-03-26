package qlw.model.copy;

import qlw.model.Department;
import qlw.model.Doctor;

import java.util.List;

/**
 * Created by wiki on 2017/3/10.
 */
public class HospitalCopy {
    private List<Department> departments;

    private List<Doctor> doctors;

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
