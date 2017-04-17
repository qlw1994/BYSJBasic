package qlw.controller.admin.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import qlw.controller.BaseController;
import qlw.manage.NumberManage;
import qlw.model.Numbers;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/23.
 */
@Controller
@RequestMapping(value = "/admin/numbers")
public class NumberController extends BaseController {
    @Autowired
    NumberManage numberManage;

    /**
     * 号源列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listNumber(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length,String startdate,String enddate,Long hospitalid,Long departmentid, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            Numbers numbers=new Numbers();
            numbers.setHospitalid(hospitalid);
            numbers.setDepartmentid(departmentid);
            result.put("total", numberManage.count(startdate,enddate,numbers));
            result.put("data", numberManage.list(page, length,startdate,enddate, numbers));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }
    //
    ///**
    // * 号源管理首页跳转
    // *
    // * @return
    // */
    //@RequestMapping(value = "/index")
    //public ModelAndView View(Long departmentid, Integer pcode, Integer subcode, HttpServletRequest request) {
    //    ModelAndView mv = new ModelAndView("admin/hospital/numbers_department");
    //    request.getSession().setAttribute("departmentid", departmentid);
    //    mv.addObject("pcode", pcode);
    //    mv.addObject("subcode", subcode);
    //    mv.addObject("currentpage", 1);
    //    return mv;
    //}
}
