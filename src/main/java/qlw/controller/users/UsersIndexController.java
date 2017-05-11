package qlw.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.UserManage;
import qlw.model.Users;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/24.
 */
@Controller
@RequestMapping(value = "/userindex")
public class UsersIndexController extends BaseController {

    @Autowired
    UserManage userManage;

    @RequestMapping(value = "")
    public String admin() {
        return "redirect:userindex/index";
    }

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("users/index");
        modelAndView.addObject("currentpage", 1);
        return modelAndView;
    }

    @RequestMapping(value = "/signup")
    public ModelAndView signup(Users users, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("users/signup");
        return modelAndView;
    }

    @RequestMapping(value = "/signupIn")
    @ResponseBody
    public Map<String, Object> signupIn(Users users, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "注册成功";
        users.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
        userManage.saveBackId(users);
        request.getSession().setAttribute("user", users);
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

    /**
     * 初始化日期
     *
     * @param starttime
     * @param endtime
     * @param request
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "initDate")
    @ResponseBody
    public Map<String, Object> statisticsBus(String starttime, String endtime, HttpServletRequest request) throws IOException {
        initController(starttime, endtime, request);
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "日期初始化成功";
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
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

    private void initController(String starttime, String endtime, HttpServletRequest request) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.hasText(starttime) && !StringUtils.hasText(endtime)) {
            Calendar ca = Calendar.getInstance();
            ca.add(Calendar.MONTH, -1);
            starttime = simpleDateFormat.format(ca.getTime());
            endtime = simpleDateFormat.format(new Date());
        }
        request.getSession().setAttribute("starttime", starttime);
        request.getSession().setAttribute("endtime", endtime);
    }
}
