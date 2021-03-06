package qlw.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.PatientManage;
import qlw.model.Patient;
import qlw.model.Users;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/4/2.
 */
@Controller
@RequestMapping(value = "/user/patients")
public class Users_patientController extends BaseController {
    @Autowired
    PatientManage patientManage;

    /**
     * 账号列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listPatient(@RequestParam(value = "page",required = false) Integer page, @RequestParam(value = "length", required = false) Integer length, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            Users users = (Users) request.getSession().getAttribute("user");
            long uid = users.getId();
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
            Users users = (Users) request.getSession().getAttribute("user");
            long uid = users.getId();
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

    @RequestMapping(value = "/patientChosen")
    public ModelAndView indexView(Long hospitalid, String hospitalname, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("users/patient_chosen");
        Users users = (Users) request.getSession().getAttribute("user");
        request.getSession().setAttribute("uid", users.getId());
        request.getSession().setAttribute("hospitalid", hospitalid);
        request.getSession().setAttribute("hospitalname", hospitalname);
        mv.addObject("currentpage", 1);
        return mv;
    }


    /**
     * 相似就诊人名称列表 (可分页)
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/listPatientLike", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listPatientLike(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "length", required = false) Integer length, String name, Long uid) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", patientManage.countLike(name, uid));
            result.put("data", patientManage.getLike(page, length, name, uid));
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
            //判断该就诊人是否已经在其他用户中建档
            if (!hasSameName(patienttable, request)) {
                rtnMsg = "该就诊人已经建档";
                //判断该就诊人是否已经在用户名下
                if (!hasPatient(patienttable, request)) {
                    patientManage.save(patienttable);
                }
                rtnCode = ResultCode.SUCCESS;
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

    ///**
    // * 删除账号
    // *
    // * @return
    // */
    //@RequestMapping(value = "delPatient/{id}", method = RequestMethod.POST)
    //@ResponseBody
    //public Map<String, Object> delPatient(@PathVariable("id") Long id) {
    //    Map<String, Object> result = new HashMap<>();
    //    Integer rtnCode = ResultCode.SUCCESS;
    //    String rtnMsg = "删除成功";
    //    try {
    //        Patient patient = patientManage.getById(id);
    //        patient.setDeletedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
    //        patientManage.update(patient);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        rtnMsg = "删除失败";
    //        rtnCode = ResultCode.ERROR;
    //    }
    //    result.put("message", rtnMsg);
    //    result.put("code", rtnCode);
    //    return result;
    //}


    /**
     * 检查是否有相同的账号名称
     *
     * @param patient
     * @return 存在false 否则true
     */
    @RequestMapping(value = "/sameName", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasSameName(Patient patient, HttpServletRequest request) {
        Users users = (Users) request.getSession().getAttribute("user");
        long uid = users.getId();
        boolean flag = patientManage.haveSameName(patient.getName(), patient.getIdnumber(), patient.getGuardianidnumber());
        return !flag;
    }
    /**
     * 该用户下就诊人是否存在
     *
     * @param patient
     * @return 存在false 否则true
     */
    @RequestMapping(value = "/hasPatient", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasPatient(Patient patient, HttpServletRequest request) {
        boolean flag = patientManage.hasPatient(patient.getName(), patient.getIdnumber(), patient.getGuardianidnumber(), patient.getUid());
        return !flag;
    }
}
