package qlw.controller.admin.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.AppointmentManage;
import qlw.manage.NumberManage;
import qlw.model.Appointment;
import qlw.model.Numbers;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/21.
 */
@Controller
@RequestMapping(value = "/admin/appointments")
public class AppointmentController extends BaseController {
    @Autowired
    AppointmentManage appointmentManage;
    @Autowired
    NumberManage numberManage;

    /**
     * 预约列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listAppointment(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, Long hospitalid, Long departmentid,
                                               Long patientid, String startdate, Long doctorid, String enddate, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            Appointment appointment = new Appointment();
            appointment.setDoctorid(doctorid);
            appointment.setDepartmentid(departmentid);
            appointment.setPatientid(patientid);
            appointment.setHospitalid(hospitalid);
            result.put("total", appointmentManage.count(startdate, enddate, appointment));
            result.put("data", appointmentManage.list(page, length, startdate, enddate, appointment));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 预约管理首页跳转 病人
     *
     * @return
     */
    @RequestMapping(value = "/patientindex")
    public ModelAndView ViewPatient(long patientid, String patientname, Long uid,int pcode, int subcode, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/appointment_patient");
        request.getSession().setAttribute("uid", uid);
        request.getSession().setAttribute("patientid", patientid);
        request.getSession().setAttribute("patientname", patientname);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }
    /**
     * 预约管理首页跳转  医生版
     *
     * @return
     */
    @RequestMapping(value = "/doctorindex")
    public ModelAndView ViewDoctor(long doctorid, String doctorname, int pcode, int subcode, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/appointment_doctor");
        request.getSession().setAttribute("doctorid", doctorid);
        request.getSession().setAttribute("doctorname", doctorname);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }
    /**
     * 预约管理首页跳转  科室
     *
     * @return
     */
    @RequestMapping(value = "/departmentindex")
    public ModelAndView ViewDepartment(long departmentid, String departmentname, int pcode, int subcode, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/appointment_department");
        request.getSession().setAttribute("departmentid", departmentid);
        request.getSession().setAttribute("departmentname", departmentname);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }
    /**
     * 添加预约
     *
     * @param appointment
     * @return
     */
    @RequestMapping(value = "newAppointment")
    @ResponseBody
    public Map<String, Object> addAppointment(Appointment appointment, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            String date = appointment.getDate();
            int timeflag = appointment.getTimeflag();
            Long departmentid = appointment.getDepartmentid();
            Numbers numbers = numberManage.getByTimeflagAndDeptidAndDate(timeflag, departmentid, date);
            if (numbers.getAppointleftcount() == 0) {
                rtnMsg = "添加失败";
                rtnCode = ResultCode.ERROR;
            } else {
                numbers.setAppointleftcount(numbers.getAppointleftcount() - 1);
                numberManage.update(numbers);
                appointmentManage.save(appointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "添加失败";
            rtnCode = ResultCode.ERROR;
        }

        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

    /**
     * 预约查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "appointmentInfo", method = RequestMethod.POST)
    @ResponseBody
    public Appointment getAppointmentInfo(Long id) {
        try {
            return appointmentManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    ///**
    // * 修改预约
    // *
    // * @return
    // */
    //@RequestMapping(value = "modAppointment", method = RequestMethod.POST)
    //@ResponseBody
    //public Map<String, Object> updateAppointment(Appointment appointment) {
    //    Map<String, Object> result = new HashMap<>();
    //    Integer rtnCode = ResultCode.SUCCESS;
    //    String rtnMsg = "修改成功";
    //    try {
    //        appointmentManage.update(appointment);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        rtnMsg = "修改失败";
    //        rtnCode = ResultCode.ERROR;
    //    }
    //    result.put("message", rtnMsg);
    //    result.put("code", rtnCode);
    //    return result;
    //}

    /**
     * 删除预约
     *
     * @return
     */
    @RequestMapping(value = "delAppointment/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delAppointment(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            Appointment appointment = appointmentManage.getById(id);
            appointment.setStatus(2);
            appointmentManage.update(appointment);
            String date = appointment.getDate();
            int timeflag = appointment.getTimeflag();
            Long departmentid = appointment.getDepartmentid();
            Numbers numbers = numberManage.getByTimeflagAndDeptidAndDate(timeflag, departmentid, date);
            numbers.setAppointleftcount(numbers.getAppointleftcount() + 1);
            numberManage.update(numbers);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "删除失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

    /**
     * 修改预约状态
     *
     * @return
     */
    @RequestMapping(value = "modAppointmentStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateAppointment(Appointment appointment,Integer status) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改预约状态->挂号状态成功";
        try {
            appointment.setStatus(status);
            appointmentManage.update(appointment);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "修改预约状态->挂号状态失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

}
