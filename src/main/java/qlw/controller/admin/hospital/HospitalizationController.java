package qlw.controller.admin.hospital;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.*;
import qlw.model.*;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wiki on 2017/4/16.
 */
@Controller
@RequestMapping(value = "/admin/hospitalizations")

public class HospitalizationController extends BaseController {

    @Autowired
    HospitalizationManage hospitalizationManage;
    @Autowired
    HospitalpayManage hospitalpayManage;
    @Autowired
    PaymentdetailManage paymentdetailManage;
    @Autowired
    DepartmentManage departmentManage;
    @Autowired
    HospitalManage hospitalManage;
    @Autowired
    PatientManage patientManage;
    @Autowired
    UserManage userManage;

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
    public ModelAndView View(Integer pcode, Integer subcode,String uname, Long patientid, String patientname, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/hospitalization");
        if (request.getParameter("uid") != null) {
            request.getSession().setAttribute("uid", Long.parseLong(request.getParameter("uid")));
        }
        request.getSession().setAttribute("patientid", patientid);
        request.getSession().setAttribute("patientname", patientname);
        mv.addObject("uname", uname);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }

    /**
     * 住院订单就诊人管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/patientChosen")
    public ModelAndView patientChosen(Integer pcode, Integer subcode, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/patient_chosen");
        request.setAttribute("service", "hospitalizations/index");
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        return mv;
    }

    /**
     * 添加住院订单
     *
     * @param hospitalization
     * @return
     */
    @RequestMapping(value = "newHospitalization")
    @ResponseBody
    @Transactional(readOnly = true)
    public Map<String, Object> addHospitalization(MultipartFile file, Hospitalization hospitalization, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {

            //List<Hospitalpay> hospitalpays = hospitalization.getHospitalpays();


            String path = request.getSession().getServletContext().getRealPath("upload");
            String fileName = new Date().getTime() + ".csv";
            File targetFile = new File(path, fileName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            String[] FILE_HEADER = {"名称", "日期", "单价", "单位", "数量", "医嘱"};
            //保存
            try {
                file.transferTo(targetFile);
            } catch (Exception e) {
                e.printStackTrace();
            }


            List<Hospitalpay> hospitalpays = new ArrayList<>();
            String FILE_NAME = path + "/" + fileName;
            // 这里显式地配置一下CSV文件的Header，然后设置跳过Header（要不然读的时候会把头也当成一条记录）
            CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord();
            BigDecimal totalMoney=new BigDecimal("0");
            try (Reader in = new FileReader(FILE_NAME)) {
                Iterable<CSVRecord> records = format.parse(in);
                for (CSVRecord record : records) {
                    Hospitalpay hospitalpay = new Hospitalpay();
                    String name = record.get("名称").trim();
                    String date = record.get("日期").trim();
                    String price = record.get("单价").trim();
                    String unit = record.get("单位").trim();
                    String amount = record.get("数量").trim();
                    String advice = record.get("医嘱").trim();
                    hospitalpay.setName(name);
                    hospitalpay.setDate(date);
                    hospitalpay.setPrice(new BigDecimal(price));
                    hospitalpay.setAmount(Integer.parseInt(amount));
                    hospitalpay.setAdvice(advice);
                    hospitalpay.setUnit(unit);
                    hospitalpay.setMoney(new BigDecimal(Double.parseDouble(price) * Integer.parseInt(amount)));
                    totalMoney=totalMoney.add(hospitalpay.getMoney());

                    hospitalpay.setStatus(0);

                    hospitalpays.add(hospitalpay);

                }
                hospitalization.setMoney(totalMoney);
                hospitalization.setStatus(0);
                hospitalizationManage.saveBackId(hospitalization);

            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Hospitalpay d : hospitalpays) {
                d.setHospitalizationid(hospitalization.getId());
                hospitalpayManage.saveBackId(d);

                Hospital hospital = hospitalManage.getById(hospitalization.getHospitalid());
                Department department = departmentManage.getById(hospitalization.getDepartmentid());
                Patient patient = patientManage.getById(hospitalization.getPatientid());
                Users users = userManage.getUsersById(patient.getUid());
                Paymentdetail paymentdetail = new Paymentdetail();
                paymentdetail.setStatus(0);
                paymentdetail.setPayname(d.getName());
                paymentdetail.setDepartmentid(hospitalization.getDepartmentid());
                paymentdetail.setDepartmentname(department.getName());
                paymentdetail.setHospitalid(hospital.getId());
                paymentdetail.setHospitalname(hospital.getName());
                paymentdetail.setCount(d.getAmount());
                paymentdetail.setFormat(d.getFormat());
                paymentdetail.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
                paymentdetail.setPatientid(hospitalization.getPatientid());
                paymentdetail.setPatientname(patient.getName());
                paymentdetail.setUid(users.getId());
                paymentdetail.setUname(users.getName());
                paymentdetail.setMoney(d.getMoney());
                paymentdetail.setUnit(d.getUnit());
                paymentdetail.setPrice(d.getPrice());
                paymentdetail.setProjectid(d.getId());
                paymentdetail.setProjecttype(1);//住院清单
                paymentdetailManage.save(paymentdetail);
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

    /**
     * 修改住院订单
     *
     * @return
     */
    @RequestMapping(value = "modHospitalization", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateHospitalization(Hospitalization hospitalization) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            hospitalizationManage.update(hospitalization);
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
     * 删除住院订单
     *
     * @return
     */
    @RequestMapping(value = "delHospitalization/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delHospitalization(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            hospitalpayManage.deleteByHospitalizationid(id);
            hospitalizationManage.delete(id);
            //Hospitalization hospitalization = hospitalizationManage.getById(id);
            //hospitalization.setDeletedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            //hospitalizationManage.update(hospitalization);
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
