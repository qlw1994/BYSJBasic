package qlw.timeTask;

import org.springframework.beans.factory.annotation.Autowired;
import qlw.controller.admin.hospital.AppointmentController;

import java.util.TimerTask;

/**
 * Created by wiki on 2017/4/18.
 */
public class TaskPM extends TimerTask {
    @Autowired
    AppointmentController appointmentController;
    public void run() {
        appointmentController.clearAppointment(null,2);//下午预约清空
    }
}