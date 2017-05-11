package qlw.timeTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qlw.controller.admin.hospital.AppointmentController;
import qlw.controller.admin.hospital.DepartmentqueueController;
import qlw.controller.admin.hospital.HospitalController;
import qlw.controller.admin.hospital.SchedulingController;
import qlw.manage.DoctorManage;
import qlw.model.Doctor;

import java.util.List;
import java.util.TimerTask;

/**
 * Created by wiki on 2017/4/18.
 */
@Component
public class Task extends TimerTask {
    @Autowired
    HospitalController hospitalController;
    @Autowired
    SchedulingController schedulingController;
    @Autowired
    DoctorManage doctorManage;
    @Autowired
    DepartmentqueueController departmentqueueController;
    @Autowired
    AppointmentController appointmentController;

    public void run() {
        hospitalController.reflashHospital();//刷新授权信息
        List<Doctor> doctors = doctorManage.listAll();
        for (Doctor d : doctors) {
            schedulingController.generateScheduling(d.getId(), d.getHospitalid(), d.getDepartmentid(), d.getLevel(), 50);
        }//号源日更新
        departmentqueueController.resetQueue(null);//刷新队列

    }
}
