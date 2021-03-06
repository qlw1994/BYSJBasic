package qlw.controller.admin.hospital;

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
 * Created by wiki on 2017/3/18.
 */
@Controller
@RequestMapping(value = "/admin/checkreports")
public class CheckreportController extends BaseController{
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
    public ModelAndView View(Integer pcode, Integer subcode, long patientid, String patientname, HttpServletRequest
            request) {
        ModelAndView mv = new ModelAndView("admin/hospital/checkreport");
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
     * 检验表管理首页跳转  就诊人入口
     *
     * @return
     */
    @RequestMapping(value = "/patientindex")
    public ModelAndView ViewPatientIndex(Integer pcode, Integer subcode, Long patientid, String patientname,Long uid, HttpServletRequest
            request) {
        ModelAndView mv = new ModelAndView("admin/account/checkreport");
        request.getSession().setAttribute("patientid", patientid);
        request.getSession().setAttribute("patientname", patientname);
        request.getSession().setAttribute("uid", uid);
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
    public ModelAndView patientChosen(Integer pcode, Integer subcode,Long doctorid, String doctorname,String service, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/patient_chosen");
        request.setAttribute("service",service);
        request.getSession().setAttribute("doctorid", doctorid);
        request.getSession().setAttribute("doctorname", doctorname);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        return mv;
    }
    /**
     * 检验表就诊人管理首页跳转 就诊人入口
     *
     * @return
     */
    @RequestMapping(value = "/patient_Chosen")
    public ModelAndView patient_Chosen(Integer pcode, Integer subcode,HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/patient_chosen");
        request.setAttribute("service","checkreports/patientindex");

        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        return mv;
    }
    /**
     * 添加检验表
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

    /**
     * 修改检验表
     *
     * @return
     */
    @RequestMapping(value = "modCheckreport", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateCheckreport(Checkreport checkreport) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            checkreportManage.update(checkreport);
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
    @RequestMapping(value = "delCheckreport/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delCheckreport(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            Checkreport checkreport = checkreportManage.getById(id);
            checkreport.setDeletedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            checkreportManage.update(checkreport);
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
