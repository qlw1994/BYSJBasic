package qlw.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.CheckreportManage;
import qlw.model.Checkreport;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/4/3.
 */
@Controller
@RequestMapping(value = "/user/checkreports")
public class Users_CheckreportController extends BaseController{
    @Autowired
    CheckreportManage checkreportManage;


    /**
     * 检验表列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listCheckreport(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, String startdate, String enddate, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            if(startdate!=null){
                request.setAttribute("starttime",startdate);
            }
            if(enddate!=null){
                request.setAttribute("endtime",enddate);
            }
            Checkreport checkreport = new Checkreport();
            String str_patientid = request.getParameter("patientid");
            String str_departmentid = request.getParameter("departmentid");
            String str_hospitalid = request.getParameter("hospitalid");
            String str_doctorid = request.getParameter("doctorid");
            String str_auditorid = request.getParameter("auditorid");
            if (str_patientid != null && !"".equals(str_patientid)) {
                long patientid = Long.parseLong(str_patientid);
                checkreport.setPatientid(patientid);
            }
            if (str_departmentid != null && !"".equals(str_departmentid)) {
                long departmentid = Long.parseLong(str_departmentid);
                checkreport.setDepartmentid(departmentid);
            }
            if (str_hospitalid != null && !"".equals(str_hospitalid)) {
                long hospitalid = Long.parseLong(str_hospitalid);
                checkreport.setHospitalid(hospitalid);
            }
            if (str_doctorid != null && !"".equals(str_doctorid)) {
                long docotorid = Long.parseLong(str_doctorid);
                checkreport.setDoctorid(docotorid);
            }
            if (str_auditorid != null && !"".equals(str_auditorid)) {
                long auditorid = Long.parseLong(str_auditorid);
                checkreport.setAuditorid(auditorid);
            }
            result.put("total", checkreportManage.count(startdate, enddate, checkreport));
            result.put("data", checkreportManage.list(page, length, startdate, enddate, checkreport));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 检验表管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View( Long patientid, String patientname, HttpServletRequest
            request) {
        ModelAndView mv = new ModelAndView("users/checkreport");
        request.getSession().setAttribute("patientid", patientid);
        request.getSession().setAttribute("patientname", patientname);
        mv.addObject("currentpage", 1);
        return mv;
    }


    /**
     * 检验表查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "checkreportInfo", method = RequestMethod.POST)
    @ResponseBody
    public Checkreport getCheckreportInfo(Long id) {
        try {
            return checkreportManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
