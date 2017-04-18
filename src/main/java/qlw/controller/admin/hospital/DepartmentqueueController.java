package qlw.controller.admin.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import qlw.controller.BaseController;
import qlw.manage.DepartmentManage;
import qlw.manage.DepartmentqueueManage;
import qlw.manage.DepartmentqueuedetailManage;
import qlw.manage.HospitalManage;
import qlw.model.Department;
import qlw.model.Departmentqueue;
import qlw.model.Hospital;
import qlw.util.ResultCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/4/4.
 */
@Controller
@RequestMapping(value = "/admin/departmentqueues")
public class DepartmentqueueController extends BaseController {
    @Autowired
    DepartmentqueueManage departmentqueueManage;
    @Autowired
    DepartmentManage departmentManage;
    @Autowired
    HospitalManage hospitalManage;
    @Autowired
    DepartmentqueuedetailManage departmentqueuedetailManage;

    /**
     * 科室队列需每日重置
     *
     * @return
     */
    @RequestMapping(value = "reset")
    public boolean resetQueue() {
        Departmentqueue departmentqueue = new Departmentqueue();
        departmentqueue.setNowtotal(0);
        departmentqueue.setNownumber(0);
        departmentqueue.setTodaytotal(0);
        try {
            departmentqueueManage.resetQueue(departmentqueue);
            departmentqueuedetailManage.deleteAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建队列
     *
     * @param departmentid
     * @return
     */
    @RequestMapping(value = "/newQueue")
    @ResponseBody
    public Map<String, Object> newQueue(Long departmentid) {
        Department department = departmentManage.getById(departmentid);
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            if (departmentqueueManage.hasQueue(departmentid)) {
                rtnMsg = "科室队列已经存在";
                rtnCode = ResultCode.ERROR;
            } else {
                Hospital hospital = hospitalManage.getById(department.getHospitalid());
                Departmentqueue departmentqueue = new Departmentqueue();
                departmentqueue.setNowtotal(0);
                departmentqueue.setNownumber(0);
                departmentqueue.setTodaytotal(0);
                departmentqueue.setDepartmentid(department.getId());
                departmentqueue.setDepartmentname(department.getName());
                departmentqueue.setHospitalid(department.getHospitalid());
                departmentqueue.setHospitalname(hospital.getName());
                departmentqueue.setAvgtime(15);
                departmentqueueManage.save(departmentqueue);
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
}
