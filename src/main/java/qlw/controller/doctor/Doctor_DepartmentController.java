package qlw.controller.doctor;

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
import qlw.manage.HospitalManage;
import qlw.model.Department;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/4/5.
 */
@Controller
@RequestMapping(value = "/doctor/departmetns")
public class Doctor_DepartmentController extends BaseController {
    @Autowired
    DepartmentManage departmentManage;
    @Autowired
    DepartmentqueueManage departmentqueueManage;
    @Autowired
    HospitalManage hospitalManage;

    /**
     * 科室列表数据源
     *
     * @returns
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDepartment(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            long hospitalid = Long.parseLong(request.getParameter("hospitalid"));
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


    @RequestMapping(value = "/listLike", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDepartmentLike(String name, long hospitalid, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", departmentManage.countLike(name, hospitalid));
            result.put("data", departmentManage.getLike(null, null, name, hospitalid));
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
    public ModelAndView accountView(Integer pcode, Integer subcode, long hospitalid, String hospitalname, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("admin/hospital/department");
        request.getSession().setAttribute("hospitalid", hospitalid);
        request.getSession().setAttribute("hospitalname", hospitalname);
        mv.addObject("currentpage", 1);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        return mv;
    }

    /**
     * 检查是否有相同的科室名称
     *
     * @param name
     * @param hospitalid
     * @return @return 存在返回false 否则true
     */
    @RequestMapping(value = "/sameName", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasSameName(String name, long hospitalid) {
        Department department = departmentManage.getByName(name, hospitalid);
        if (department == null || department.getDeletedate() == null) {
            return true;
        }
        return false;
    }


}
