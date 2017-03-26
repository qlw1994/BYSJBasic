package qlw.model.copy;

import qlw.model.Department;
import qlw.model.Hospital;
import qlw.model.Patient;
import qlw.model.Users;

/**
 * Created by wiki on 2017/3/23.
 */
public class AppointmentCopy {
    private Patient patient;
    private Hospital hospital;
    private Department department;
    private Users users;

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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
