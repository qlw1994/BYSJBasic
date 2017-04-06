package qlw.controller.admin.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.DepartmentManage;
import qlw.manage.DepartmentqueueManage;
import qlw.manage.HospitalManage;
import qlw.model.Department;
import qlw.model.Departmentqueue;
import qlw.model.Hospital;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by wiki on 2017/3/6.
 */
@Controller
@RequestMapping(value = "/admin/departments")
public class DepartmentController extends BaseController {
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
     * 添加科室
     *
     * @param department
     * @return
     */
    @RequestMapping(value = "newDepartment")
    @ResponseBody
    public Map<String, Object> addDepartment(Department department, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            Department departmentTemp = departmentManage.getByName(department.getName(), department.getHospitalid());
            if (departmentTemp != null) {
                departmentTemp.setDeletedate(null);
                departmentManage.delete(departmentTemp.getId());
                departmentManage.save(departmentTemp);
            } else {
                department.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
                departmentManage.save(department);
            }
            Hospital hospital = hospitalManage.getById(department.getHospitalid());
            Departmentqueue departmentqueue = new Departmentqueue();
            departmentqueue.setNownumber(0);
            departmentqueue.setNowtotal(0);
            departmentqueue.setTodaytotal(0);
            departmentqueue.setDepartmentid(department.getId());
            departmentqueue.setDepartmentname(department.getName());
            departmentqueue.setHospitalid(department.getHospitalid());
            departmentqueue.setHospitalname(hospital.getName());
            departmentqueue.setAvgtime(15);
            departmentqueueManage.save(departmentqueue);
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
     * 修改科室
     *
     * @return
     */
    @RequestMapping(value = "modDepartment", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDepartment(Department department, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            departmentManage.update(department);
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
     * 删除科室
     *
     * @return
     */
    @RequestMapping(value = "delDepartment/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delDepartment(@PathVariable("id") Long id, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            Department department = departmentManage.getById(id);
            department.setDeletedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            departmentManage.update(department);
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
     * 科室首页跳转
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView accountView(int pcode, int subcode, long hospitalid, String hospitalname, HttpServletRequest request) {

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

    @RequestMapping(value = "/initDepartmentqueue")
    @ResponseBody
    public Map<String, Object> initDepartmentqueue(Long hospitalid, String hospitalname) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "重置科室队列成功";
        try {
            List<Department> departments = departmentManage.listAll(hospitalid);
            departmentqueueManage.deleteByHospitalid(hospitalid);
            for (Department department : departments) {
                Departmentqueue departmentqueue = new Departmentqueue();
                departmentqueue.setHospitalid(hospitalid);
                departmentqueue.setDepartmentid(department.getId());
                departmentqueue.setDepartmentname(department.getName());
                departmentqueue.setHospitalname(hospitalname);
                departmentqueue.setNowtotal(0);
                departmentqueue.setNownumber(0);
                departmentqueue.setTodaytotal(0);
                departmentqueue.setAvgtime(15);
                departmentqueueManage.save(departmentqueue);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "重置科室队列失败";
            rtnCode = ResultCode.ERROR;
        }

        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }
}
