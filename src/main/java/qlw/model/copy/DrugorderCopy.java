package qlw.model.copy;

import qlw.model.Department;
import qlw.model.Drugorderdetail;
import qlw.model.Hospital;

import java.util.List;

/**
 * Created by wiki on 2017/3/15.
 */
public class DrugorderCopy {
    Hospital hospital;
    Department department;
    List<Drugorderdetail> drugorderdetails;

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

    public List<Drugorderdetail> getDrugorderdetails() {
        return drugorderdetails;
    }

    public void setDrugorderdetails(List<Drugorderdetail> drugorderdetails) {
        this.drugorderdetails = drugorderdetails;
    }
}
