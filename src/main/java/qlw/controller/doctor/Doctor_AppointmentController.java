package qlw.controller.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.*;
import qlw.model.Appointment;
import qlw.model.Departmentqueue;
import qlw.model.Departmentqueuedetail;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/25.
 */
@Controller
@RequestMapping(value = "/doctor/appointments")
public class Doctor_AppointmentController extends BaseController {
    @Autowired
    AppointmentManage appointmentManage;
    @Autowired
    NumberManage numberManage;
    @Autowired
    SchedulingManage schedulingManage;
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
            if (startdate != null && !startdate.equals("")) {
                startdate = MyUtils.SIMPLE_DATE_FORMAT.format(new Date());
            }
            if (enddate != null &&!enddate.equals("")) {
                enddate = MyUtils.SIMPLE_DATE_FORMAT.format(new Date());
            }
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
     * 预约管理首页跳转  医生版
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView ViewDoctor(Long doctorid, String doctorname, Integer pcode, Integer subcode, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("doctor/appointment");
        request.getSession().setAttribute("doctorid", doctorid);
        request.getSession().setAttribute("doctorname", doctorname);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
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
     * 修改预约状态
     *
     * @return
     */
    @RequestMapping(value = "modAppointmentStatus", method = RequestMethod.POST)
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
}
