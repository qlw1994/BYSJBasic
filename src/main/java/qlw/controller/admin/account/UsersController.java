package qlw.controller.admin.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.UserManage;
import qlw.model.Users;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/1/24.
 */
@Controller
@RequestMapping(value = "/admin/users")
public class UsersController extends BaseController {
    @Autowired
    UserManage userManage;

    /**
     * 账号列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listUser(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", userManage.count());
            result.put("data", userManage.list(page, length));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 用户管理首页跳转
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView accountView(Integer pcode, Integer subcode, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/account/useraccount");
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }

    /**
     * 相似用户名称列表
     *
     * @param account
     * @return
     */
    @RequestMapping(value = "/listUsersLike", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listUsersLike(String account) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", userManage.countLike(account));
            result.put("data", userManage.getUsersLike(account));
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
     * @param usertable
     * @return
     */
    @RequestMapping(value = "newUser")
    @ResponseBody
    public Map<String, Object> addUser(Users usertable, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            Users users = userManage.getUsersByAccount(usertable.getAccount());
            //usertable.setPassword(MD5Utils.getMD5(usertable.getPassword()));
            if (users != null) {
                users.setDeletedate(null);
                userManage.delete(users.getId());
                userManage.save(users);
            } else {
                usertable.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
                userManage.save(usertable);
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

    /**
     * 删除账号
     *
     * @return
     */
    @RequestMapping(value = "delUser/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delUser(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            Users users = userManage.getUsersById(id);
            users.setDeletedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            userManage.updateUsers(users);
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
     * @param account
     * @return 存在返回false
     */
    @RequestMapping(value = "/sameName", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasSameName(String account) {
        boolean res = userManage.haveSameAccount(account);
        return !res;
    }

    /**
     * 检查是否存在账号名称
     *
     * @param account
     * @return 存在返回true
     */
    @RequestMapping(value = "/hasName", method = RequestMethod.POST)
    @ResponseBody
    public boolean hasName(String account) {
        boolean res = userManage.haveSameAccount(account);
        return res;
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
        return userManage.sameOddPassword(id, password);
    }

    /**
     * 更新密码
     *
     * @param newPassword
     * @param users
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public int updatePassword(String newPassword, Users users) {
        //newPassword = MD5Utils.getMD5(newPassword);
        users.setPassword(newPassword);
        return userManage.updateUsers(users);
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
            Users users = userManage.getUsersById(id);
            users.setPassword("123456");
            //users.setPassword(MD5Utils.getMD5("123456"));
            userManage.updateUsers(users);
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
