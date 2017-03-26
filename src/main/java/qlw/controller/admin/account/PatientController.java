package qlw.controller.admin.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.PatientManage;
import qlw.model.Patient;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/11.
 */
@Controller
@RequestMapping(value = "/admin/patients")
public class PatientController extends BaseController {
    @Autowired
    PatientManage patientManage;

    /**
     * 账号列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listPatient(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            long uid = Long.parseLong(request.getParameter("uid"));
            result.put("total", patientManage.count(uid));
            result.put("data", patientManage.list(page, length, uid));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 账号列表数据源 全部
     *
     * @return
     */
    @RequestMapping(value = "listAll", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listPatientAll(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            long uid = Long.parseLong(request.getParameter("uid"));
            result.put("total", patientManage.count(uid));
            result.put("data", patientManage.listAll(uid));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }


    /**
     * 就诊人管理首页跳转
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView accountView(int pcode, int subcode, Long uid, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/account/patient");
            request.getSession().setAttribute("uid", uid);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }

    /**
     * 相似就诊人名称列表
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/listPatientLike", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listPatientLike(String name, long uid) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", patientManage.countLike(name, uid));
            result.put("data", patientManage.getLike(name, uid));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 添加帐号
     *
     * @param patienttable
     * @return
     */
    @RequestMapping(value = "newPatient")
    @ResponseBody
    public Map<String, Object> addPatient(Patient patienttable, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            if (!hasSameName(patienttable, request)) {
                rtnMsg = "存在相同的就诊人";
                rtnCode = ResultCode.ERROR;
            } else {
                Patient patient = patientManage.getByName(patienttable.getName(), (Long) request.getSession().getAttribute("uid"));
                if (patient != null) {
                    patient.setDeletedate(null);
                    patientManage.delete(patient.getId());
                    patientManage.save(patient);
                } else {
                    patienttable.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
                    patientManage.save(patienttable);
                }
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
     * 帐号查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "patientInfo", method = RequestMethod.POST)
    @ResponseBody
    public Patient getPatientInfo(Long id) {
        try {
            return patientManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改账号
     *
     * @return
     */
    @RequestMapping(value = "modPatient", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updatePatient(Patient patient, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";

        try {
            patientManage.update(patient);
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
     * 删除账号
     *
     * @return
     */
    @RequestMapping(value = "delPatient/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delPatient(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            Patient patient = patientManage.getById(id);
            patient.setDeletedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            patientManage.update(patient);
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
     * 检查是否有相同的账号名称
     *
     * @param patient
     * @return 存在false 否则true
     */
    @RequestMapping(value = "/sameName", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasSameName(Patient patient, HttpServletRequest request) {
        boolean flag = patientManage.haveSameName(patient.getName(), patient.getIdnumber(), patient.getGuardianidnumber(), (Long) request.getSession().getAttribute("uid"));
        return !flag;
    }

}
