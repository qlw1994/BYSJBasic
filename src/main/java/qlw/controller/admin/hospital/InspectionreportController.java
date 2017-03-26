package qlw.controller.admin.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.manage.InspectionreportManage;
import qlw.manage.InspectitemManage;
import qlw.model.Inspectionreport;
import qlw.model.Inspectitems;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by wiki on 2017/3/18.
 */
@Controller
@RequestMapping(value = "/admin/inspectionreports")
public class InspectionreportController {
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
    public Map<String, Object> listInspectionreport(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, @RequestParam(value = "startDate", defaultValue = "") String startDate, @RequestParam(value = "endDate", defaultValue = "") String endDate, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
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
            result.put("total", inspectionreportManage.count(startDate, endDate, inspectionreport));
            result.put("data", inspectionreportManage.list(page, length, startDate, endDate, inspectionreport));
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
    public ModelAndView View(int pcode, int subcode, long patientid, String patientname, HttpServletRequest
            request) {
        ModelAndView mv = new ModelAndView("admin/hospital/inspectreport");
        if (request.getParameter("uid") != null) {
            request.getSession().setAttribute("uid", Long.parseLong(request.getParameter("uid")));
        }
        request.getSession().setAttribute("patientid", patientid);
        request.getSession().setAttribute("patientname", patientname);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }

    /**
     * 检验表就诊人管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/patientChosen")
    public ModelAndView patientChosen(long doctorid, String doctorname,String service, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/patient_chosen");
        request.getSession().setAttribute("doctorid", doctorid);
        request.getSession().setAttribute("doctorname", doctorname);
        request.setAttribute("service",service);
        return mv;
    }

    /**
     * 添加检验表
     *
     * @param inspectionreport
     * @return
     */
    @RequestMapping(value = "newInspectionreport")
    @ResponseBody
    public Map<String, Object> addInspectionreport(Inspectionreport inspectionreport, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            inspectionreport.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            inspectionreportManage.saveBackId(inspectionreport);
            List<Inspectitems> inspectitems = inspectionreport.getInspectitemss();
            for (Inspectitems d : inspectitems) {
                d.setInspectionid(inspectionreport.getId());
                inspectitemManage.save(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "添加失败";
            rtnCode = ResultCode.ERROR;
        }

        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
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

    /**
     * 修改检验表
     *
     * @return
     */
    @RequestMapping(value = "modInspectionreport", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateInspectionreport(Inspectionreport inspectionreport) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            inspectionreportManage.update(inspectionreport);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "修改失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

    /**
     * 删除检验表
     *
     * @return
     */
    @RequestMapping(value = "delInspectionreport/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delInspectionreport(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            Inspectionreport inspectionreport = inspectionreportManage.getById(id);
            inspectionreport.setDeletedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            inspectionreportManage.update(inspectionreport);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "删除失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

}
