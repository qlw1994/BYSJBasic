package qlw.controller.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import qlw.controller.BaseController;
import qlw.manage.DepartmentManage;
import qlw.manage.DoctorManage;
import qlw.manage.HospitalManage;
import qlw.model.Department;
import qlw.model.Doctor;
import qlw.model.Hospital;
import qlw.util.CommonUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/1/24.
 */
@Controller
public class DoctorLoginController extends BaseController {
    @Autowired
    DoctorManage doctorManange;
    @Autowired
    DepartmentManage departmentManage;
    @Autowired
    HospitalManage hospitalManage;

    @RequestMapping(value = "/doctorlogin")
    public String login() {
        return "doctor/doctorlogin";
    }

    @RequestMapping(value = "/doctorindex/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@RequestParam("account") String account, @RequestParam("password") String password, HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();

        if (CommonUtils.isNull(account) || CommonUtils.isNull(password)) {
            resMap.put("code", ResultCode.PARAMETERS_EMPTY);
            resMap.put("msg", "传入参数不能为空");
            return resMap;
        }
        Doctor doctor = doctorManange.getDoctorByAccount(account);
        if (doctor == null) {
            resMap.put("code", ResultCode.ERROR);
            resMap.put("msg", "未找到该用户或该用户已经注销");
            return resMap;
        }
        //password = MD5Utils.getMD5(password);
        if (password.equals(doctor.getPassword())) {
            Hospital hospital = hospitalManage.getById(doctor.getHospitalid());
            Department department = departmentManage.getById(doctor.getDepartmentid());
            doctor.setHospital(hospital);
            doctor.setDepartment(department);
            request.getSession().setAttribute("doctor", doctor);
            resMap.put("code", ResultCode.SUCCESS);
            resMap.put("msg", "登录成功");
            return resMap;
        } else {
            resMap.put("code", ResultCode.ERROR);
            resMap.put("msg", "密码不对");
            return resMap;
        }
    }

    @RequestMapping(value = "/doctorsignup")
    public Map<String, Object> signup(Doctor doctor, HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        doctorManange.saveBackId(doctor);
        request.getSession().setAttribute("doctor", doctor);
        resMap.put("code", ResultCode.SUCCESS);
        resMap.put("msg", "注册成功");
        return resMap;
    }

    @RequestMapping(value = "/doctorindex/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("doctor");
        return "doctor/doctorlogin";
    }
}
