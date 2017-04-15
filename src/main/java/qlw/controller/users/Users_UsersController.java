package qlw.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qlw.controller.BaseController;
import qlw.manage.UserManage;
import qlw.model.Users;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/4/10.
 */
@Controller
@RequestMapping(value = "/user/users")
public class Users_UsersController extends BaseController {
    @Autowired
    UserManage userManage;

    /**
     * 帐号查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "userInfo", method = RequestMethod.POST)
    @ResponseBody
    public Users getUserInfo(Long id) {
        try {
            return userManage.getUsersById(id);
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
    @RequestMapping(value = "modUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateUser(Users users, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";

        try {
            userManage.updateUsers(users);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "修改失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }
}
