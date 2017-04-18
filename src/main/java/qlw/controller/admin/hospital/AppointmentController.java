package qlw.controller.admin.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.*;
import qlw.model.*;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    SchedulingManage schedulingManage;
    @Autowired
    PatientManage patientManage;
    @Autowired
    DepartmentqueueManage departmentqueueManage;
    @Autowired
    DepartmentqueuedetailManage departmentqueuedetailManage;

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
    public ModelAndView ViewPatient(long patientid, String patientname, Long uid, Integer pcode, Integer subcode, HttpServletRequest request) {
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
     * 预约管理首页跳转 病人
     *
     * @return
     */
    @RequestMapping(value = "/patientchosen")
    public ModelAndView ViewPatientChosen(Long schedulingid, Integer pcode, Integer subcode, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/appointment_patient_chosen");
        request.setAttribute("schedulingid", schedulingid);
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
    public ModelAndView ViewDoctor(long doctorid, String doctorname, Integer pcode, Integer subcode, HttpServletRequest request) {
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
    public ModelAndView ViewDepartment(long departmentid, String departmentname, Integer pcode, Integer subcode, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/appointment_department");
        request.getSession().setAttribute("departmentid", departmentid);
        request.getSession().setAttribute("departmentname", departmentname);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }

    /**
     * 添加预约 （普通挂号也能指定医生）
     *
     * @param schedulingid
     * @return
     */
    @RequestMapping(value = "newAppointment")
    @ResponseBody
    public Map<String, Object> newAppointment(Long schedulingid, Long patientid, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            Scheduling scheduling = schedulingManage.getById(schedulingid);
            if (appointmentManage.patientHasAppointment(patientid)) {

                rtnMsg = "该用户存在其他未完成的预约";
                rtnCode = ResultCode.ERROR;
            } else {
                Patient patient = patientManage.getById(patientid);
                String date = scheduling.getDate();
                int timeflag = scheduling.getTimeflag();
                int type = scheduling.getType();
                Long departmentid = scheduling.getDepartmentid();
                Paymentdetail paymentdetail = new Paymentdetail();
                if (scheduling.getAppointleftcount() == 0) {
                    rtnMsg = "剩余可预约号源不足,添加失败";
                    rtnCode = ResultCode.ERROR;
                } else {
                    String departmentname = (String) request.getSession().getAttribute("departmentname");
                    String hospitalname = (String) request.getSession().getAttribute("hospitalname");
                    String doctorname = (String) request.getSession().getAttribute("doctorname");
                    Appointment appointment = new Appointment();

                    appointment.setDepartmentname(departmentname);
                    appointment.setHospitalname(hospitalname);
                    appointment.setDoctorname(doctorname);
                    appointment.setDate(date);
                    appointment.setTimeflag(timeflag);
                    appointment.setType(type);
                    appointment.setDepartmentid(departmentid);
                    appointment.setPatientid(patientid);
                    appointment.setPatientname(patient.getName());
                    appointment.setHospitalid(scheduling.getHospitalid());
                    appointment.setCommittime(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
                    appointment.setDoctorid(scheduling.getDoctorid());
                    appointment.setUid(patient.getUid());
                    appointment.setStatus(1);
                    appointment.setRegfee(scheduling.getRegfee());
                    appointment.setSchedulingid(scheduling.getId());
                    scheduling.setAppointleftcount(scheduling.getAppointleftcount() - 1);

                    schedulingManage.update(scheduling);
                    appointmentManage.save(appointment);
                }
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

    ///**
    // * 添加预约  （普通挂号 随机分配版）
    // *
    // * @param appointment
    // * @return
    // */
    //@RequestMapping(value = "newAppointment")
    //@ResponseBody
    //public Map<String, Object> addAppointment(Appointment appointment, HttpServletRequest request) {
    //    Map<String, Object> result = new HashMap<>();
    //    Integer rtnCode = ResultCode.SUCCESS;
    //    String rtnMsg = "添加成功";
    //    try {
    //        String date = appointment.getDate();
    //        int timeflag = appointment.getTimeflag();
    //        int type = appointment.getType();
    //        Long departmentid = appointment.getDepartmentid();
    //        Paymentdetail paymentdetail = new Paymentdetail();
    //        Numbers numbers = numberManage.getByTimeflagAndDeptidAndDate(timeflag, departmentid, date, type);
    //        if (numbers.getAppointleftcount() == 0) {
    //            rtnMsg = "剩余可预约号源不足,添加失败";
    //            rtnCode = ResultCode.ERROR;
    //        } else {
    //            numbers.setAppointleftcount(numbers.getAppointleftcount() - 1);
    //            numberManage.update(numbers);
    //            appointmentManage.save(appointment);
    //        }
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        rtnMsg = "添加失败";
    //        rtnCode = ResultCode.ERROR;
    //    }
    //
    //    result.put("message", rtnMsg);
    //    result.put("code", rtnCode);
    //    return result;
    //}

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

    //   /**
    // * 删除预约  （普通挂号随机分配医生版）
    // *
    // * @return
    // */
    //@RequestMapping(value = "delAppointment/{id}", method = RequestMethod.POST)
    //@ResponseBody
    //public Map<String, Object> delAppointment(@PathVariable("id") Long id) {
    //    Map<String, Object> result = new HashMap<>();
    //    Integer rtnCode = ResultCode.SUCCESS;
    //    String rtnMsg = "删除成功";
    //    try {
    //
    //
    //        Appointment appointment = appointmentManage.getById(id);
    //        appointment.setStatus(2);
    //        int type = appointment.getType();
    //        appointmentManage.update(appointment);
    //        String date = appointment.getDate();
    //        int timeflag = appointment.getTimeflag();
    //        Long departmentid = appointment.getDepartmentid();
    //        Numbers numbers = numberManage.getByTimeflagAndDeptidAndDate(timeflag, departmentid, date, type);
    //        numbers.setAppointleftcount(numbers.getAppointleftcount() + 1);
    //        numberManage.update(numbers);
    //        //如果是专家需要在 专家个人号源上删除
    //        if (type == 1) {
    //            Scheduling scheduling = schedulingManage.getByDateAndTimeflagAndDoctorid(date, timeflag, appointment.getDoctorid());
    //            scheduling.setLeftnumber(scheduling.getLeftnumber() + 1);
    //            schedulingManage.update(scheduling);
    //        }
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        rtnMsg = "删除失败";
    //        rtnCode = ResultCode.ERROR;
    //    }
    //    result.put("message", rtnMsg);
    //    result.put("code", rtnCode);
    //    return result;
    //}

    /**
     * 删除预约  （普通挂号 也能指定医生版）
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
            int type = appointment.getType();
            appointmentManage.update(appointment);


            String date = appointment.getDate();
            int timeflag = appointment.getTimeflag();
            Scheduling scheduling = schedulingManage.getByDateAndTimeflag(date, timeflag, type).get(0);
            scheduling.setAppointleftcount(scheduling.getAppointleftcount() + 1);


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
     * 修改预约状态   清理当日过期 预约
     *
     * @return
     */
    @RequestMapping(value = "clearAppointment", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> clearAppointment(Long hospitalid, int timeflag) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改预约状态成功";
        try {
            appointmentManage.clearAppointrment(hospitalid, timeflag);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "修改预约状态失败";
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
    @RequestMapping(value = "modAppointmentStatus")
    @ResponseBody
    public Map<String, Object> updateAppointment(Long id, Integer status) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改预约状态成功";
        try {
            Appointment appointment = appointmentManage.getById(id);
            appointment.setStatus(status);
            Departmentqueue departmentqueue = departmentqueueManage.getByDepartmentid(appointment.getDepartmentid());
            Departmentqueuedetail departmentqueuedetail = new Departmentqueuedetail();
            //确认取号
            if (status.equals(new Integer(4))) {
                //第一人来挂号的人
                if (departmentqueue.getNowtotal().equals(new Integer(0)) && departmentqueue.getTodaytotal().equals(new Integer(0))) {
                    departmentqueue.setNowtotal(1);
                    departmentqueue.setNownumber(1);
                    departmentqueue.setTodaytotal(1);
                    departmentqueuedetail.setNumber(1);
                    appointment.setSerialnumber(1);
                }
                //队伍空 但不是第一个来挂号的人
                else if (departmentqueue.getNowtotal().equals(new Integer(0))) {
                    departmentqueue.setNowtotal(1);
                    //今天历史人数
                    Integer todaytotal = departmentqueue.getTodaytotal();
                    departmentqueuedetail.setNumber(todaytotal + 1);
                    appointment.setSerialnumber(todaytotal + 1);
                    departmentqueue.setTodaytotal(todaytotal + 1);

                    departmentqueue.setNownumber(todaytotal + 1);
                }
                //队伍不为空
                else {
                    departmentqueue.setNowtotal(departmentqueue.getNowtotal() + 1);
                    //今天历史人数
                    Integer todaytotal = departmentqueue.getTodaytotal();
                    departmentqueuedetail.setNumber(todaytotal + 1);
                    appointment.setSerialnumber(todaytotal + 1);
                    departmentqueue.setTodaytotal(todaytotal + 1);
                }
                departmentqueuedetail.setDepartmentqueueid(departmentqueue.getId());
                departmentqueuedetail.setPatientid(appointment.getPatientid());
                departmentqueuedetail.setPatientname(appointment.getPatientname());

                departmentqueuedetailManage.save(departmentqueuedetail);

            }
            //诊断完毕 队列更新
            else if (status.equals(new Integer(7))) {

                departmentqueuedetailManage.deleteByPatienid(appointment.getPatientid());
                departmentqueue.setNowtotal(departmentqueue.getNowtotal()-1);
                //队列中和还有人
                if (!departmentqueue.getNowtotal().equals(new Integer(0))) {
                    departmentqueuedetail = departmentqueuedetailManage.getNext(departmentqueue.getId());
                    departmentqueue.setNownumber(departmentqueuedetail.getNumber());
                    departmentqueue.setTodaytotal(departmentqueue.getTodaytotal() + 1);
                }
                //队列没有人
                else {
                    departmentqueue.setNownumber(0);
                }
            }
            departmentqueueManage.update(departmentqueue);
            appointmentManage.update(appointment);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "修改预约状态失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

}
