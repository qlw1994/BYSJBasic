package qlw.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.HospitalizationManage;
import qlw.model.Hospitalization;
import qlw.model.Users;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/4/17.
 */
@Controller
@RequestMapping(value = "/user/hospitalizations")
public class Users_HospitalizationController extends BaseController {
    @Autowired
    HospitalizationManage hospitalizationManage;


    /**
     * 住院订单列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listHospitalization(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, String startdate, String edndate, Long patientid, Long hospitalid, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            //long patientid = (Long) request.getSession().getAttribute("patientid");
            Hospitalization hospitalization = new Hospitalization();
            hospitalization.setPatientid(patientid);
            hospitalization.setHospitalid(hospitalid);
            result.put("total", hospitalizationManage.count(startdate, edndate, hospitalization));
            result.put("data", hospitalizationManage.list(page, length, startdate, edndate, hospitalization));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 住院订单管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(long patientid, String patientname, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("usres/hospitalization");
        request.getSession().setAttribute("patientid", patientid);
        request.getSession().setAttribute("patientname", patientname);
        return mv;
    }

    /**
     * 住院订单就诊人管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/patientChosen")
    public ModelAndView patientChosen(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("users/index_patient_chosen");
        Users users = (Users) request.getSession().getAttribute("user");
        request.getSession().setAttribute("uid", users.getId());
        request.setAttribute("service","hospitalizations/index");
        return mv;
    }

    /**
     * 住院订单查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "hospitalizationInfo", method = RequestMethod.POST)
    @ResponseBody
    public Hospitalization getHospitalizationInfo(Long id) {
        try {
            return hospitalizationManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
