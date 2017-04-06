package qlw.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import qlw.controller.BaseController;
import qlw.manage.DepartmentqueueManage;
import qlw.manage.DepartmentqueuedetailManage;
import qlw.model.Departmentqueue;
import qlw.model.Departmentqueuedetail;

import java.util.HashMap;
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
     * 获取当前就诊人
     *
     * @param patientid
     * @return
     */
    @RequestMapping(value = "/getQueue")
    @ResponseBody
    public Map<String, Object> getQueue(Long patientid) {
        Map<String, Object> result = new HashMap<>();
        Departmentqueuedetail departmentqueuedetail = departmentqueuedetailManage.getByPatientid(patientid);
        if (departmentqueuedetail == null) {
            result.put("total", 0);
        } else {
            Departmentqueue departmentqueue = departmentqueueManage.getById(departmentqueuedetail.getDepartmentqueueid());
            result.put("total", 1);
            result.put("data", departmentqueue.getNownumber());
        }
        return result;
    }
}
