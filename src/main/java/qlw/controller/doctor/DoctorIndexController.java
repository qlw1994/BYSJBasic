package qlw.controller.doctor;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.model.Doctor;
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
@RequestMapping(value = "/doctor")
public class DoctorIndexController extends BaseController {


    @RequestMapping(value = "")
    public String doctor() {
        return "redirect:doctor/index";
    }

    @RequestMapping(value = "index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("doctor/index");
        return modelAndView;
    }

    @RequestMapping(value = "/signup")
    public ModelAndView signup(Doctor doctor, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("doctor/signup");
        return modelAndView;
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
