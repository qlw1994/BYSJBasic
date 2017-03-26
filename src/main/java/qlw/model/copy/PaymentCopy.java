package qlw.model.copy;

import qlw.model.Department;
import qlw.model.Hospital;
import qlw.model.Patient;
import qlw.model.Paymentdetail;

import java.util.List;

/**
 * Created by wiki on 2017/3/22.
 */
public class PaymentCopy {
    private List<Paymentdetail> paymentdetails;

    private Department department;

    private Hospital hospital;

    private Patient patient;

    public List<Paymentdetail> getPaymentdetails() {
        return paymentdetails;
    }

    public void setPaymentdetails(List<Paymentdetail> paymentdetails) {
        this.paymentdetails = paymentdetails;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
}
