package qlw.controller.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.CheckreportManage;
import qlw.model.Checkreport;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/4/3.
 */
@Controller
@RequestMapping(value = "/doctor/checkreports")
public class Doctor_CheckreportController extends BaseController{
    @Autowired
    CheckreportManage checkreportManage;


    /**
     * 检查表列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listCheckreport(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, @RequestParam(value = "startDate", defaultValue = "") String startDate, @RequestParam(value = "endDate", defaultValue = "") String endDate, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
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
            result.put("total", checkreportManage.count(startDate, endDate, checkreport));
            result.put("data", checkreportManage.list(page, length, startDate, endDate, checkreport));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 检查表管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(Integer pcode, Integer subcode, Long patientid, String patientname, Long uid,HttpServletRequest
            request) {
        ModelAndView mv = new ModelAndView("doctor/checkreport");
        request.getSession().setAttribute("uid", uid);
        request.getSession().setAttribute("patientid", patientid);
        request.getSession().setAttribute("patientname", patientname);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }


    /**
     * 添加检查表
     *
     * @param checkreport
     * @return
     */
    @RequestMapping(value = "newCheckreport")
    @ResponseBody
    public Map<String, Object> addCheckreport(Checkreport checkreport, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            checkreport.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            checkreport.setExamtime(MyUtils.SIMPLE_DATETIME_FORMAT.format(new Date(checkreport.getExamtime())));
            checkreport.setChecktime((MyUtils.SIMPLE_DATETIME_FORMAT.format(new Date(checkreport.getChecktime()))));
            checkreportManage.save(checkreport);
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
     * 检查表查询
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

    ///**
    // * 修改检查表
    // *
    // * @return
    // */
    //@RequestMapping(value = "modCheckreport", method = RequestMethod.POST)
    //@ResponseBody
    //public Map<String, Object> updateCheckreport(Checkreport checkreport) {
    //    Map<String, Object> result = new HashMap<>();
    //    Integer rtnCode = ResultCode.SUCCESS;
    //    String rtnMsg = "修改成功";
    //    try {
    //        checkreportManage.update(checkreport);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        rtnMsg = "修改失败";
    //        rtnCode = ResultCode.ERROR;
    //    }
    //    result.put("message", rtnMsg);
    //    result.put("code", rtnCode);
    //    return result;
    //}
    //
    ///**
    // * 删除检查表
    // *
    // * @return
    // */
    //@RequestMapping(value = "delCheckreport/{id}", method = RequestMethod.POST)
    //@ResponseBody
    //public Map<String, Object> delCheckreport(@PathVariable("id") Long id) {
    //    Map<String, Object> result = new HashMap<>();
    //    Integer rtnCode = ResultCode.SUCCESS;
    //    String rtnMsg = "删除成功";
    //    try {
    //        Checkreport checkreport = checkreportManage.getById(id);
    //        checkreport.setDeletedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
    //        checkreportManage.update(checkreport);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        rtnMsg = "删除失败";
    //        rtnCode = ResultCode.ERROR;
    //    }
    //    result.put("message", rtnMsg);
    //    result.put("code", rtnCode);
    //    return result;
    //}

}
