package qlw.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.*;
import qlw.model.Hospitalpay;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/4/17.
 */
@Controller
@RequestMapping(value = "user/hospitalpays")
public class Users_HospitalpayController extends BaseController {

    @Autowired
    HospitalpayManage hospitalpayManage;

    /**
     * 住院消费订单详情列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listHospitalpay(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            long hospitalizationid = Long.parseLong(request.getParameter("hospitalizationid"));
            result.put("total", hospitalpayManage.count(hospitalizationid));
            result.put("data", hospitalpayManage.list(page, length, hospitalizationid));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 住院消费订单详情管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(Long hospitalizationid,Long hospitalid,String hospitalname, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("users/hospitalpay");
        request.getSession().setAttribute("hospitalizationid", hospitalizationid);
        request.getSession().setAttribute("hospitalid", hospitalid);
        request.getSession().setAttribute("hospitalname", hospitalname);
        mv.addObject("currentpage", 1);
        return mv;
    }


    /**
     * 住院消费订单详情查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "hospitalpayInfo", method = RequestMethod.POST)
    @ResponseBody
    public Hospitalpay getHospitalpayInfo(Long id) {
        try {
            return hospitalpayManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
