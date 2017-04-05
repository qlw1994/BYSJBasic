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
import qlw.model.Drugorder;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/4/3.
 */
@Controller
@RequestMapping(value = "/user/drugorders")
public class Users_drugorderController extends BaseController {
    @Autowired
    DrugorderManage drugorderManage;
    @Autowired
    DrugorderdetailManage drugorderdetailManage;

    /**
     * 药品订单列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDrugorder(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, @RequestParam(value = "startDate", defaultValue = "") String startdate, @RequestParam(value = "endDate", defaultValue = "") String enddate, Long patientid, Long hospitalid, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            //long patientid = (Long) request.getSession().getAttribute("patientid");
            Drugorder drugorder = new Drugorder();
            drugorder.setPatientid(patientid);
            drugorder.setHospitalid(hospitalid);
            result.put("total", drugorderManage.count(startdate, enddate, drugorder));
            result.put("data", drugorderManage.list(page, length, startdate, enddate, drugorder));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 药品订单管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(Long patientid, String patientname, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("users/drugorder");
        request.getSession().setAttribute("patientid", patientid);
        request.getSession().setAttribute("patientname", patientname);
        mv.addObject("currentpage", 1);
        return mv;
    }


    /**
     * 药品订单查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "drugorderInfo", method = RequestMethod.POST)
    @ResponseBody
    public Drugorder getDrugorderInfo(Long id) {
        try {
            return drugorderManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
