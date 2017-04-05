package qlw.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import qlw.controller.BaseController;
import qlw.manage.UserManage;
import qlw.model.Users;
import qlw.util.CommonUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/1/24.
 */
@Controller
public class UsersLoginController extends BaseController {
    @Autowired
    UserManage userManange;


    @RequestMapping(value = "userlogin")
    public String login() {
        return "users/userlogin";
    }

    @RequestMapping(value = "/userindex/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@RequestParam("account") String account, @RequestParam("password") String password, HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();

        if (CommonUtils.isNull(account) || CommonUtils.isNull(password)) {
            resMap.put("code", ResultCode.PARAMETERS_EMPTY);
            resMap.put("msg", "传入参数不能为空");
            return resMap;
        }
        Users user = userManange.getUsersByAccount(account);
        if (user == null) {
            resMap.put("code", ResultCode.ERROR);
            resMap.put("msg", "未找到该用户或该用户已经注销");
            return resMap;
        }

//        if (user.getFlag() == 0) {
//            resMap.put("code", ResultCode.ERROR);
//            resMap.put("msg", "该用户已被注销");
//            return resMap;
//        }
//
//        //用户角色无权查看
//        if (user.getPower() == 1) {
//            resMap.put("code", ResultCode.ERROR);
//            resMap.put("msg", "该用户无权限登录本系统");
//            return resMap;
//        }

        //password = MD5Utils.getMD5(password);
        if (password.equals(user.getPassword())) {
            request.getSession().setAttribute("user", user);
            resMap.put("code", ResultCode.SUCCESS);
            resMap.put("msg", "登录成功");
            return resMap;
        } else {
            resMap.put("code", ResultCode.ERROR);
            resMap.put("msg", "未找到该用户或该用户已经注销");
            return resMap;
        }
    }

    @RequestMapping(value = "/userindex/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "userindex/index";
    }
}
