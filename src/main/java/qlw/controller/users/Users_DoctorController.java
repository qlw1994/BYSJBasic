package qlw.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.DoctorManage;
import qlw.manage.NumberManage;
import qlw.manage.SchedulingManage;
import qlw.model.Doctor;
import qlw.model.Scheduling;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/30.
 */
@Controller
@RequestMapping(value = "/user/doctors")
public class Users_DoctorController extends BaseController {
    @Autowired
    DoctorManage doctorManage;
    @Autowired
    NumberManage numberManage;
    @Autowired
    SchedulingManage schedulingManage;

    /**
     * 医院-科室-医生管理首页跳转
     *
     * @param pcode
     * @param subcode
     * @param departmentid
     * @param request
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView hospitalindexView(int pcode, int subcode, long departmentid, String departmentname, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/doctor");
        request.getSession().setAttribute("departmentid", departmentid);
        request.getSession().setAttribute("departmentname", departmentname);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }

    /**
     * 账号列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDoctor(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            long departmentid = Long.parseLong(request.getParameter("departmentid"));
            result.put("total", doctorManage.countByDepartment(departmentid));
            result.put("data", doctorManage.listByDepartment(page, length, departmentid));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }


    /**
     * 相似医生姓名列表 按科室
     *
     * @param name
     * @returns
     */
    @RequestMapping(value = "/listNameLikeByDepartment", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listNameLike(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "length", required = false) Integer length, String name, Long hospitalid) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", doctorManage.countNameLikeByDepartment(name, hospitalid));
            result.put("data", doctorManage.getNameLikeByDepartment(page, length, name, hospitalid));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 帐号查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "doctorInfo", method = RequestMethod.POST)
    @ResponseBody
    public Doctor getDoctorInfo(Long id) {
        try {
            return doctorManage.getDoctorById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 检查是否存在名称 单家医院范围
     *
     * @param name
     * @return 相同false，否则true
     */
    @RequestMapping(value = "/hasName", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasName(String name, long hospitalid) {
        boolean result = doctorManage.haveSameName(name, hospitalid);
        if (!result) {
            return true;
        }
        return false;
    }

    /**
     * 获取之后7天的排班
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getSchedulings")
    @ResponseBody
    public Map<String, Object> getNumbersnext7day(Long id) {
        Map<String, Object> result = new HashMap<>();
        Scheduling scheduling = new Scheduling();
        scheduling.setDoctorid(id);
        try {
            result.put("total", 7);
            result.put("data", schedulingManage.listNext7Day(scheduling));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        return result;
    }

}
