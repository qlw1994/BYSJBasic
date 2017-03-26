package qlw.controller.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.FixedschedulingManage;
import qlw.manage.NumberManage;
import qlw.manage.SchedulingManage;
import qlw.model.Scheduling;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/25.
 */
@Controller
@RequestMapping(value = "/doctor/schedulings")
public class Doctor_SchedulinglController extends BaseController {
    @Autowired
    SchedulingManage schedulingManage;
    @Autowired
    NumberManage numberManage;
    @Autowired
    FixedschedulingManage fixedschedulingManage;

    /**
     * 排班列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listScheduling(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, @RequestParam(value = "startDate", defaultValue = "") String startDate, @RequestParam(value = "endDate", defaultValue = "") String endDate, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            Scheduling scheduling = new Scheduling();
            if (request.getParameter("hospitalid") != null) {
                long hospitalid = Long.parseLong(request.getParameter("hospitalid"));
                scheduling.setHospitalid(hospitalid);
            }

            if (request.getParameter("departmentid") != null) {
                long departmentid = Long.parseLong(request.getParameter("departmentid"));
                scheduling.setDepartmentid(departmentid);
            }
            if (request.getParameter("doctorid") != null) {
                long doctorid = Long.parseLong(request.getParameter("doctorid"));
                scheduling.setDoctorid(doctorid);
            }
            //List<Date> dates = MyUtils.dateToWeek(new Date(), flag);
            result.put("total", schedulingManage.count(startDate, endDate, scheduling));
            result.put("data", schedulingManage.list(page, length, startDate, endDate, scheduling));

        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 排班管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(long docotorid, String doctorname, int pcode, int subcode, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("admin/hospital/scheduling");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss|EEE");

        request.getSession().setAttribute("docotorid", docotorid);
        request.getSession().setAttribute("doctorname", doctorname);

        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        return mv;
    }


    /**
     * 添加排班
     *
     * @param scheduling
     * @return
     */
    @RequestMapping(value = "newScheduling")
    @ResponseBody
    public Map<String, Object> addScheduling(Scheduling scheduling, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            if (schedulingManage.haveSameScheduling(scheduling)) {
                rtnCode = ResultCode.ERROR;
                rtnMsg = "添加失败";
            } else {
                schedulingManage.save(scheduling);
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
     * 排班查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "schedulingInfo", method = RequestMethod.POST)
    @ResponseBody
    public Scheduling getSchedulingInfo(Long id) {
        try {
            return schedulingManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改排班
     *
     * @return
     */
    @RequestMapping(value = "modScheduling", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateScheduling(Scheduling scheduling) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            schedulingManage.update(scheduling);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "修改失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

    /**
     * 删除排班
     *
     * @return
     */
    @RequestMapping(value = "delScheduling/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delScheduling(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            schedulingManage.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "删除失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

}
