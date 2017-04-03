package qlw.controller.doctor;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.InspectionreportManage;
import qlw.manage.InspectitemManage;
import qlw.model.Doctor;
import qlw.model.Inspectionreport;
import qlw.model.Inspectitems;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/4/3.
 */
@Controller
@RequestMapping(value = "/doctor/inspectionreports")
public class Doctor_InspectionreportController extends BaseController {
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
     * 固定排班文件上传
     *
     * @return
     */
    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> fileupload(MultipartFile file, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "上传成功";
        String path = request.getSession().getServletContext().getRealPath("upload");
        String fileName = new Date().getTime() + ".csv";
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String[] FILE_HEADER = {"名称", "结果", "参考范围", "单位", "结果异常提示"};
        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Inspectionreport inspectionreport = new Inspectionreport();
        Doctor doctor = (Doctor) request.getSession().getAttribute("doctor");
        inspectionreport.setDoctorid(doctor.getId());
        inspectionreport.setHospitalid(doctor.getHospitalid());
        inspectionreport.setDepartmentid(doctor.getDepartmentid());
        inspectionreport.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
        inspectionreport.setAuditorid(doctor.getId());
        inspectionreport.setExamtime(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
        inspectionreport.setDate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
        inspectionreportManage.saveBackId(inspectionreport);
        Long inspectionid = inspectionreport.getId();
        String FILE_NAME = path + "/" + fileName;
        // 这里显式地配置一下CSV文件的Header，然后设置跳过Header（要不然读的时候会把头也当成一条记录）
        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord();
        try (Reader in = new FileReader(FILE_NAME)) {
            Iterable<CSVRecord> records = format.parse(in);
            for (CSVRecord record : records) {
                Inspectitems inspectitems = new Inspectitems();
                inspectitems.setInspectionid(inspectionid);
                String name = record.get("名称").trim();
                String inspectresult = record.get("结果").trim();
                String refrange = record.get("参考范围").trim();
                String unit = record.get("单位").trim();
                String abnormal = record.get("结果异常提示").trim();
                inspectitems.setName(name);
                inspectitems.setRefrange(refrange.equals("#") ? null : refrange);
                inspectitems.setResult(inspectresult.equals("#") ? null : inspectresult);
                inspectitems.setUnit(unit.equals("#") ? null : unit);
                int abnormal_int;
                switch (abnormal) {
                    case "正常":
                        abnormal_int = 0;

                        break;
                    case "偏高":
                        abnormal_int = 1;

                        break;
                    case "偏低":
                        abnormal_int = 2;

                        break;
                    case "阴性":
                        abnormal_int = 3;

                        break;
                    default:
                        abnormal_int = 4;

                        break;
                }
                inspectitems.setAbnormal(abnormal_int);
                inspectitemManage.save(inspectitems);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

}
