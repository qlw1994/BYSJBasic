package qlw.controller.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.*;
import qlw.model.Doctor;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/26.
 */
@Controller
@RequestMapping(value = "/doctor/doctors")
public class Doctor_DoctorController extends BaseController {
    @Autowired
    DoctorManage doctorManage;
    @Autowired
    DepartmentManage departmentManage;


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
        ModelAndView mv = new ModelAndView("doctor/doctor");
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
    public Map<String, Object> updateDoctor(Doctor doctors, MultipartFile file, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";

        try {

            String path = request.getSession().getServletContext().getRealPath("upload");
            String fileName = String.valueOf(new Date().getTime()) + String.valueOf(doctors.getId()) + ".jpg";
            File targetFile = new File(path, fileName);
            Doctor doctor_headpath = doctorManage.getDoctorById(doctors.getId());
            String old_headpath = doctor_headpath.getHeadpath();
            //删除旧图像
            if (old_headpath != null) {
                String[] str = old_headpath.split("/");
                old_headpath = str[str.length - 1];
                //old_headpath = old_headpath.replace("/", "\\\\");
                File deleteFile = new File(path, old_headpath);
                deleteFile.delete();
            }
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            //保存
            try {
                file.transferTo(targetFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //String headpath = path + "\\" + fileName;
            //headpath = headpath.replaceAll("\\\\", "/");
            doctors.setHeadpath(("/upload/" + fileName));
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
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/resetPWD/{id}", method = RequestMethod.POST)
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

    /**
     * 相似医生名称列表 按科室
     *
     * @param account
     * @returns
     */
    @RequestMapping(value = "/listDoctorLikeByDepartment", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDoctorLikeByDepartment(String account, long departmentid) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", doctorManage.countLikeByDepartment(account, departmentid));
            result.put("data", doctorManage.getDoctorLikeByDepartment(account, departmentid));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 相似医生名称列表 按医院
     *
     * @param account
     * @returns
     */
    @RequestMapping(value = "/listDoctorLikeByHospital", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDoctorLikeByHospital(String account, long hospitalid) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", doctorManage.countLikeByHospital(account, hospitalid));
            result.put("data", doctorManage.getDoctorLikeByHospital(account, hospitalid));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 科室名称是否存在
     *
     * @param hospitalid
     * @param name
     * @return 存在返回flase
     */
    @RequestMapping(value = "/hasDepartmentName", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasDepartmentName(Long hospitalid, String name) {
        boolean res = departmentManage.haveSameName(name, hospitalid);
        return !res;
    }

    /**
     * 检查是否存在account 单家医院范围
     *
     * @param account
     * @return 存在true，否则false
     */
    @RequestMapping(value = "/hasAccount", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasAccount(String account, Long hospitalid) {
        boolean result = doctorManage.haveSameAccountAndHospital(account, hospitalid);

        return result;
    }


}
