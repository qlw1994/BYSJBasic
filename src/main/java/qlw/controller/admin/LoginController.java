package qlw.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import qlw.controller.BaseController;
import qlw.manage.SysuserManage;
import qlw.model.Sysusers;
import qlw.util.CommonUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/1/24.
 */
@Controller
public class LoginController extends BaseController {
    @Autowired
    SysuserManage sysuserManange;


    @RequestMapping(value = "/adminlogin")
    public String login() {
        return "admin/adminlogin";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@RequestParam("account") String account, @RequestParam("password") String password, HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();

        if (CommonUtils.isNull(account) || CommonUtils.isNull(password)) {
            resMap.put("code", ResultCode.PARAMETERS_EMPTY);
            resMap.put("msg", "传入参数不能为空");
            return resMap;
        }
        Sysusers sysuser = sysuserManange.getSysusersByAccount(account);
        if (sysuser == null) {
            resMap.put("code", ResultCode.ERROR);
            resMap.put("msg", "未找到该用户或该用户已经注销");
            return resMap;
        }
        if (password.equals(sysuser.getPassword())) {
            request.getSession().setAttribute("sysuser", sysuser);
            resMap.put("code", ResultCode.SUCCESS);
            resMap.put("msg", "登录成功");
            return resMap;
        } else {
            resMap.put("code", ResultCode.ERROR);
            resMap.put("msg", "未找到该用户或该用户已经注销");
            return resMap;
        }
    }

    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("sysuser");
        return "admin/adminlogin";
    }
}
