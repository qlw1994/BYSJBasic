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
import qlw.model.Drugorderdetail;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/4/3.
 */
@Controller
@RequestMapping(value = "/user/drugorderdetails")
public class Users_drugorderdetailController extends BaseController {
    @Autowired
    DrugorderManage drugorderManage;
    @Autowired
    DrugorderdetailManage drugorderdetailManage;
    @Autowired
    PaymentdetailManage paymentdetailManage;
    @Autowired
    DepartmentManage departmentManage;
    @Autowired
    HospitalManage hospitalManage;
    @Autowired
    DoctorManage doctorManage;
    @Autowired
    PatientManage patientManage;
    @Autowired
    UserManage userManage;

    /**
     * 药品订单详情列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDrugorderdetail(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length,Long drugorderid, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("total", drugorderdetailManage.count(drugorderid));
            result.put("data", drugorderdetailManage.list(page, length, drugorderid));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 药品订单详情管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(Long drugorderid, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("users/drugorderdetail");
        request.getSession().setAttribute("drugorderid", drugorderid);
        mv.addObject("currentpage", 1);
        return mv;
    }


    /**
     * 药品订单详情查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "drugorderdetailInfo", method = RequestMethod.POST)
    @ResponseBody
    public Drugorderdetail getDrugorderdetailInfo(Long id) {
        try {
            return drugorderdetailManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
