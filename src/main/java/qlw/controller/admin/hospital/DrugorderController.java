package qlw.controller.admin.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.*;
import qlw.model.*;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by wiki on 2017/3/15.
 */
@Controller
@RequestMapping(value = "/admin/drugorders")
public class DrugorderController extends BaseController {
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
     * 药品订单列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listDrugorder(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, String startdate, String edndate, Long patientid, Long hospitalid, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            //long patientid = (Long) request.getSession().getAttribute("patientid");
            Drugorder drugorder = new Drugorder();
            drugorder.setPatientid(patientid);
            drugorder.setHospitalid(hospitalid);
            result.put("total", drugorderManage.count(startdate, edndate, drugorder));
            result.put("data", drugorderManage.list(page, length, startdate, edndate, drugorder));
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
    public ModelAndView View(Integer pcode, Integer subcode, long patientid, String patientname, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/drugorder");
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
     * 药品订单就诊人管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/patientChosen")
    public ModelAndView patientChosen(Long doctorid, String doctorname, String service, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/patient_chosen");
        request.setAttribute("service", service);
        request.getSession().setAttribute("doctorid", doctorid);
        request.getSession().setAttribute("doctorname", doctorname);
        return mv;
    }

    /**
     * 药品订单就诊人管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/patientindex")
    public ModelAndView patientIndex(Long patientid, String patientname, String service, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/account/drugorder");
        request.getSession().setAttribute("patientid", patientid);
        request.getSession().setAttribute("patientname", patientname);
        return mv;
    }

    /**
     * 添加药品订单
     *
     * @param drugorder
     * @return
     */
    @RequestMapping(value = "newDrugorder")
    @ResponseBody
    @Transactional
    public Map<String, Object> addDrugorder(Drugorder drugorder, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            drugorder.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));

            drugorder.setNeedpay(drugorder.getTotal());

            drugorderManage.saveBackId(drugorder);
            List<Drugorderdetail> drugorderdetails = drugorder.getDrugorderdetails();
            for (Drugorderdetail d : drugorderdetails) {
                d.setDrugorderid(drugorder.getId());
                drugorderdetailManage.saveBackId(d);

                Hospital hospital = hospitalManage.getById(drugorder.getHospitalid());
                Department department = departmentManage.getById(drugorder.getDepartmentid());
                Doctor doctor = doctorManage.getDoctorById(drugorder.getDoctorid());
                Patient patient = patientManage.getById(drugorder.getPatientid());
                Users users = userManage.getUsersById(patient.getUid());
                Paymentdetail paymentdetail = new Paymentdetail();
                paymentdetail.setStatus(0);

                paymentdetail.setPayname(d.getDrugname());

                paymentdetail.setDepartmentid(drugorder.getDepartmentid());
                paymentdetail.setDepartmentname(department.getName());
                paymentdetail.setHospitalid(hospital.getId());
                paymentdetail.setHospitalname(hospital.getName());
                paymentdetail.setDoctorid(doctor.getId());
                paymentdetail.setDoctorname(doctor.getName());
                paymentdetail.setCount(d.getAmount());
                paymentdetail.setFormat(d.getFormat());
                paymentdetail.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
                paymentdetail.setPatientid(drugorder.getPatientid());
                paymentdetail.setPatientname(patient.getName());
                paymentdetail.setUid(users.getId());
                paymentdetail.setUname(users.getName());
                paymentdetail.setMoney(d.getMoney());
                paymentdetail.setPrice(d.getPrice());
                paymentdetail.setProjectid(d.getId());
                paymentdetail.setProjecttype(0);//药品
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

    /**
     * 修改药品订单
     *
     * @return
     */
    @RequestMapping(value = "modDrugorder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDrugorder(Drugorder drugorder) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            drugorderManage.update(drugorder);
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
     * 删除药品订单
     *
     * @return
     */
    @RequestMapping(value = "delDrugorder/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delDrugorder(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            departmentManage.delete(id);
            //Drugorder drugorder = drugorderManage.getById(id);
            //drugorder.setDeletedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            //drugorderManage.update(drugorder);
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
