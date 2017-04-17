package qlw.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.DepartmentqueueManage;
import qlw.manage.DepartmentqueuedetailManage;
import qlw.model.Departmentqueue;
import qlw.model.Departmentqueuedetail;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wiki on 2017/4/3.
 */
@Controller
@RequestMapping(value = "/user/departmentqueues")
public class Users_DepartmentqueueController extends BaseController {
    @Autowired
    DepartmentqueueManage departmentqueueManage;
    @Autowired
    DepartmentqueuedetailManage departmentqueuedetailManage;

    /**
     * 队列查询表管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(Long patientid, String patientname, HttpServletRequest
            request) {
        ModelAndView mv = new ModelAndView("users/departmentqueue");
        request.getSession().setAttribute("patientid", patientid);
        request.getSession().setAttribute("patientname", patientname);
        mv.addObject("currentpage", 1);
        return mv;
    }

    /**
     * 获取当前就诊人
     *
     * @param patientid
     * @return
     */
    @RequestMapping(value = "/getQueue")
    @ResponseBody
    public Map<String, Object> getQueue(Long patientid) {
        Map<String, Object> result = new HashMap<>();
        List<Departmentqueuedetail> departmentqueuedetails = departmentqueuedetailManage.getByPatientid(patientid);
        if (departmentqueuedetails == null || departmentqueuedetails.size() == 0) {
            result.put("total", 0);
        } else {
            Departmentqueue departmentqueue = departmentqueueManage.getById(departmentqueuedetails.get(0).getDepartmentqueueid());
            result.put("total", 1);
            result.put("data", departmentqueue.getNownumber());
        }
        return result;
    }
}
