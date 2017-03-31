package qlw.controller.users;

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
 * Created by wiki on 2017/3/31.
 */
@Controller
@RequestMapping(value = "/user/schedulings")
public class Users_SchedulingController extends BaseController {
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
    public ModelAndView ViewPT(Long departmentid, String departmentname,Integer type , HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("users/scheduling_index");

        request.getSession().setAttribute("departmentid", departmentid);
        request.getSession().setAttribute("departmentname", departmentname);
        request.setAttribute("currentpage", 1);
        return mv;
    }

    /**
     * 排班管理首页跳转 普通
     *
     * @return
     */
    @RequestMapping(value = "/indexPT")
    public ModelAndView ViewPT(Long departmentid, String departmentname,  HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("users/numbers_indexPT");

        request.getSession().setAttribute("departmentid", departmentid);
        request.getSession().setAttribute("departmentname", departmentname);
        request.setAttribute("currentpage", 1);
        return mv;
    }

    /**
     * 排班管理首页跳转 专家
     *
     * @return
     */
    @RequestMapping(value = "/indexZJ")
    public ModelAndView ViewZJ(Long departmentid, String departmentname,HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("users/scheduling_indexZJ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|EEE");

        request.getSession().setAttribute("departmentid", departmentid);
        request.getSession().setAttribute("departmentname", departmentname);
        request.setAttribute("currentpage", 1);
        return mv;
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

}
