package qlw.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.InspectionreportManage;
import qlw.manage.InspectitemManage;
import qlw.model.Inspectionreport;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/4/3.
 */
@Controller
@RequestMapping(value = "/user/inspectionreports")
public class Users_InspectionreportController extends BaseController {
    @Autowired
    InspectionreportManage inspectionreportManage;
    @Autowired
    InspectitemManage inspectitemManage;

    /**
     * 检验表列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listInspectionreport(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length,  String startdate,  String enddate, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            if(startdate!=null){
                request.setAttribute("starttime",startdate);
            }
            if(enddate!=null){
                request.setAttribute("endtime",enddate);
            }
            Inspectionreport inspectionreport = new Inspectionreport();
            String str_patientid = request.getParameter("patientid");
            String str_departmentid = request.getParameter("departmentid");
            String str_hospitalid = request.getParameter("hospitalid");
            String str_doctorid = request.getParameter("doctorid");
            String str_auditorid = request.getParameter("auditorid");
            if (str_patientid != null && !"".equals(str_patientid)) {
                long patientid = Long.parseLong(str_patientid);
                inspectionreport.setPatientid(patientid);
            }
            if (str_departmentid != null && !"".equals(str_departmentid)) {
                long departmentid = Long.parseLong(str_departmentid);
                inspectionreport.setDepartmentid(departmentid);
            }
            if (str_hospitalid != null && !"".equals(str_hospitalid)) {
                long hospitalid = Long.parseLong(str_hospitalid);
                inspectionreport.setHospitalid(hospitalid);
            }
            if (str_doctorid != null && !"".equals(str_doctorid)) {
                long docotorid = Long.parseLong(str_doctorid);
                inspectionreport.setDoctorid(docotorid);
            }
            if (str_auditorid != null && !"".equals(str_auditorid)) {
                long auditorid = Long.parseLong(str_auditorid);
                inspectionreport.setAuditorid(auditorid);
            }
            result.put("total", inspectionreportManage.count(startdate, enddate, inspectionreport));
            result.put("data", inspectionreportManage.list(page, length, startdate, enddate, inspectionreport));
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
    public ModelAndView View(Long patientid, String patientname, HttpServletRequest
            request) {
        ModelAndView mv = new ModelAndView("users/inspectionreport");
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
    @RequestMapping(value = "inspectionreportInfo", method = RequestMethod.POST)
    @ResponseBody
    public Inspectionreport getInspectionreportInfo(Long id) {
        try {
            return inspectionreportManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
