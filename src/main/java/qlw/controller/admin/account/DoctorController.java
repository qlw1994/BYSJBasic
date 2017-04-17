package qlw.controller.admin.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.DoctorManage;
import qlw.model.Doctor;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/3.
 */
@Controller
@RequestMapping(value = "/admin/doctors")
public class DoctorController extends BaseController {
    @Autowired
    DoctorManage doctorManage;

    /**
     * 账号列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDoctor(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", doctorManage.count());
            result.put("data", doctorManage.list(page, length));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 医生管理首页跳转
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView accountView(Integer pcode, Integer subcode, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/account/doctoraccount");
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);

        return mv;
    }

    /**
     * 相似医生名称列表
     *
     * @param account
     * @returns
     */
    @RequestMapping(value = "/listDoctorLike", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDoctorLike(String account) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", doctorManage.countLike(account));
            result.put("data", doctorManage.getDoctorLike(account));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    ///**
    // * 添加帐号
    // *
    // * @param doctor
    // * @return
    // */
    //@RequestMapping(value = "newDoctor")
    //@ResponseBody
    //public Map<String, Object> addDoctor(Doctor doctor, HttpServletRequest request) {
    //    Map<String, Object> result = new HashMap<>();
    //    Integer rtnCode = ResultCode.SUCCESS;
    //    String rtnMsg = "添加成功";
    //    try {
    //        Doctor doctorTemp = doctorManage.getDoctorByAccount(doctor.getAccount());
    //        doctor.setPassword(MD5Utils.getMD5(doctor.getPassword()));
    //        if (doctorTemp != null) {
    //            doctor.setDeletedate(null);
    //            doctorManage.updateDoctor(doctor);
    //        } else {
    //            doctor.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
    //            doctorManage.save(doctor);
    //        }
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        rtnMsg = "添加失败";
    //        rtnCode = ResultCode.ERROR;
    //    }
    //
    //    result.put("message", rtnMsg);
    //    result.put("code", rtnCode);
    //    return result;
    //}

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
     * 修改账号
     *
     * @return
     */
    @RequestMapping(value = "modDoctor", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDoctor(Doctor doctors, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";

        try {
            doctorManage.updateDoctor(doctors);
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
    @RequestMapping(value = "delDoctor/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delDoctor(@PathVariable("id") Long id, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            Doctor doctor = doctorManage.getDoctorById(id);
            doctor.setDeletedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            doctorManage.updateDoctor(doctor);
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
     * 检查是否有相同的账号名称  单家医院范围
     *
     * @param account
     * @return 相同false，否则true
     */
    @RequestMapping(value = "/sameName", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasSameName(String account, long hospitalid) {
        boolean result = doctorManage.haveSameAccountAndHospital(account, hospitalid);
        if (!result) {
            return true;
        }
        return false;
    }

    /**
     * 检查旧密码是否相同
     *
     * @param id
     * @param password
     * @return 相同返回false 否则true
     */
    @RequestMapping(value = "/sameOddPassword", method = RequestMethod.POST)
    @ResponseBody
    public Boolean sameOddPassword(Long id, String password) {
        //password = MD5Utils.getMD5(password);
        return doctorManage.sameOddPassword(id, password);
    }

    /**
     * 更新密码
     *
     * @param newPassword
     * @param doctors
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public int updatePassword(String newPassword, Doctor doctors) {
        //newPassword = MD5Utils.getMD5(newPassword);
        doctors.setPassword(newPassword);
        return doctorManage.updateDoctor(doctors);
    }
    /**
     * 重置密码
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/resetPWD/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> resetPWD(@PathVariable("id") Long id, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "密码重置成功";
        try {
            Doctor doctor = doctorManage.getDoctorById(id);
            doctor.setPassword("123456");
            //doctor.setPassword(MD5Utils.getMD5("123456"));
            doctorManage.updateDoctor(doctor);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "密码重置失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }
}
