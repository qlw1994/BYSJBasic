package qlw.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.DepartmentManage;
import qlw.manage.DepartmentqueueManage;
import qlw.model.Department;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/30.
 */
@Controller
@RequestMapping(value = "/user/departments")
public class Users_DepartmentController extends BaseController {
    @Autowired
    DepartmentManage departmentManage;
    @Autowired
    DepartmentqueueManage departmentqueueManage;

    /**
     * 科室列表数据源
     *
     * @returns
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDepartment(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, Long hospitalid, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", departmentManage.count(hospitalid));
            result.put("data", departmentManage.list(page, length, hospitalid));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 相似 医院列表
     *
     * @param name
     * @param hospitalid
     * @param request
     * @return
     */
    @RequestMapping(value = "/listLike", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDepartmentLike(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "length", required = false) Integer length, String name, Long hospitalid, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", departmentManage.countLike(name, hospitalid));
            result.put("data", departmentManage.getLike(page, length, name, hospitalid));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 科室查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "departmentInfo", method = RequestMethod.POST)
    @ResponseBody
    public Department getDepartmentInfo(Long id) {
        try {
            return departmentManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 科室首页跳转
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView accountView(Long hospitalid, String hospitalname, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("users/department");
        Date tomorrow = new Date();
        long ftime = tomorrow.getTime() + 1 * 24 * 3600000;
        tomorrow.setTime(ftime);
        String tomorrowStr = MyUtils.SIMPLE_DATE_FORMAT.format(tomorrow);
        request.getSession().setAttribute("hospitalid", hospitalid);
        request.getSession().setAttribute("hospitalname", hospitalname);
        request.getSession().setAttribute("nextdate", tomorrowStr);
        mv.addObject("currentpage", 1);
        return mv;
    }


}
