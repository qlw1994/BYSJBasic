package qlw.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qlw.controller.BaseController;
import qlw.manage.NumberManage;
import qlw.model.Numbers;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/31.
 */
@Controller
@RequestMapping(value = "/user/numbers")
public class Users_NumberController extends BaseController {
    @Autowired
    NumberManage numberManage;

    /**
     * 号源列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listNumber(Long departmentid, Integer type, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            Date now = new Date();
            Date end = new Date();
            long ftime = now.getTime() + 6 * 24 * 3600000;
            end.setTime(ftime);
            String nowStr = MyUtils.SIMPLE_DATE_FORMAT.format(now);
            String endStr = MyUtils.SIMPLE_DATE_FORMAT.format(end);

            Numbers numbers = new Numbers();
            numbers.setDepartmentid(departmentid);
            numbers.setType(type);
            result.put("total", numberManage.count(nowStr, endStr, numbers));
            result.put("data", numberManage.list(null, null, nowStr, endStr, numbers));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }
}
