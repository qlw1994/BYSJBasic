package qlw.controller.admin.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.*;
import qlw.model.*;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiki on 2017/3/15.
 */
@Controller
@RequestMapping(value = "/admin/drugorderdetails")
public class DrugorderdetailController extends BaseController {
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
    public Map<String, Object> listDrugorderdetail(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            long drugorderid = Long.parseLong(request.getParameter("drugorderid"));
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
    public ModelAndView View(int pcode, int subcode, long drugorderid, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/drugorderdetail");
        request.getSession().setAttribute("drugorderid", drugorderid);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }


    /**
     * 添加药品订单详情
     *
     * @param drugorderdetail
     * @return
     */
    @RequestMapping(value = "newDrugorderdetail")
    @ResponseBody
    public Map<String, Object> addDrugorderdetail(Drugorderdetail drugorderdetail, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            Long drugorderid = drugorderdetail.getDrugorderid();
            Drugorder drugorder = drugorderManage.getById(drugorderid);
            drugorder.setTotal(drugorder.getTotal() + 1);
            drugorder.setMoney(drugorder.getMoney().add(drugorderdetail.getMoney()));
            drugorderManage.update(drugorder);
            drugorderdetailManage.save(drugorderdetail);

            Hospital hospital = hospitalManage.getById(drugorder.getHospitalid());
            Department department = departmentManage.getById(drugorder.getDepartmentid());
            Doctor doctor = doctorManage.getDoctorById(drugorder.getDoctorid());
            Patient patient = patientManage.getById(drugorder.getPatientid());
            Users users = userManage.getUsersById(patient.getUid());
            Paymentdetail paymentdetail = new Paymentdetail();
            paymentdetail.setStatus(0);
            paymentdetail.setDepartmentid(drugorder.getDepartmentid());
            paymentdetail.setDepartmentname(department.getName());
            paymentdetail.setHospitalid(hospital.getId());
            paymentdetail.setHospitalname(hospital.getName());
            paymentdetail.setDoctorid(doctor.getId());
            paymentdetail.setDoctorname(doctor.getName());
            paymentdetail.setCount(drugorderdetail.getAmount());
            paymentdetail.setFormat(drugorderdetail.getFormat());
            paymentdetail.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            paymentdetail.setPatientid(drugorder.getPatientid());
            paymentdetail.setPatientname(patient.getName());
            paymentdetail.setUid(users.getId());
            paymentdetail.setUname(users.getName());
            paymentdetail.setMoney(drugorderdetail.getMoney());
            paymentdetailManage.save(paymentdetail);
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

    /**
     * 修改药品订单详情
     *
     * @return
     */
    @RequestMapping(value = "modDrugorderdetail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDrugorderdetail(Drugorderdetail drugorderdetail, Object odd_money) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            long drugorderid = drugorderdetail.getDrugorderid();
            Drugorderdetail oddDrugorderdetail = new Drugorderdetail();
            oddDrugorderdetail = drugorderdetailManage.getById(drugorderdetail.getId());
            BigDecimal oddMoney = BigDecimal.valueOf(Double.parseDouble((String) odd_money));

            Drugorder drugorder = drugorderManage.getById(drugorderid);
            Hospital hospital = hospitalManage.getById(drugorder.getHospitalid());
            Department department = departmentManage.getById(drugorder.getDepartmentid());
            Doctor doctor = doctorManage.getDoctorById(drugorder.getDoctorid());
            Patient patient = patientManage.getById(drugorder.getPatientid());
            Users users = userManage.getUsersById(patient.getUid());
            Paymentdetail paymentdetail = new Paymentdetail();
            paymentdetail.setMoney(oddDrugorderdetail.getMoney());
            paymentdetail.setPatientid(drugorder.getPatientid());
            paymentdetail.setPatientname(patient.getName());
            paymentdetail.setUid(users.getId());
            paymentdetail.setUname(users.getName());
            paymentdetail.setDepartmentid(department.getId());
            paymentdetail.setDepartmentname(department.getName());
            paymentdetail.setDoctorid(doctor.getId());
            paymentdetail.setDoctorname(doctor.getName());
            paymentdetail.setHospitalid(hospital.getId());
            paymentdetail.setHospitalname(hospital.getName());
            paymentdetail.setCount(oddDrugorderdetail.getAmount());
            paymentdetail.setFormat(oddDrugorderdetail.getFormat());
            paymentdetail.setCreatedate(drugorder.getCreatedate());

            Paymentdetail newpaymentdetail = new Paymentdetail();
            newpaymentdetail.setCount(drugorderdetail.getAmount());
            newpaymentdetail.setMoney(drugorderdetail.getMoney());
            newpaymentdetail.setFormat(drugorderdetail.getFormat());
            if (paymentdetailManage.updateByPaymentdetail(paymentdetail, newpaymentdetail) == -100) {
                rtnMsg = "该订单已经支付";
                rtnCode = ResultCode.ERROR;
            } else {
                drugorder.setMoney((drugorder.getMoney().subtract(oddMoney)).add(drugorderdetail.getMoney()));
                drugorderManage.update(drugorder);
                drugorderdetailManage.update(drugorderdetail);
            }
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
     * 删除药品订单详情
     *
     * @return
     */
    @RequestMapping(value = "delDrugorderdetail/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delDrugorderdetail(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            Drugorderdetail drugorderdetail = drugorderdetailManage.getById(id);
            Drugorder drugorder = drugorderManage.getById(drugorderdetail.getDrugorderid());
            drugorder.setTotal(drugorder.getTotal() - 1);
            //删除支付
            Hospital hospital = hospitalManage.getById(drugorder.getHospitalid());
            Department department = departmentManage.getById(drugorder.getDepartmentid());
            Doctor doctor = doctorManage.getDoctorById(drugorder.getDoctorid());
            Patient patient = patientManage.getById(drugorder.getPatientid());
            Users users = userManage.getUsersById(patient.getUid());
            Paymentdetail paymentdetail = new Paymentdetail();
            paymentdetail.setMoney(drugorderdetail.getMoney());
            paymentdetail.setPatientid(drugorder.getPatientid());
            paymentdetail.setPatientname(patient.getName());
            paymentdetail.setUid(users.getId());
            paymentdetail.setUname(users.getName());
            paymentdetail.setDepartmentid(department.getId());
            paymentdetail.setDepartmentname(department.getName());
            paymentdetail.setDoctorid(doctor.getId());
            paymentdetail.setDoctorname(doctor.getName());
            paymentdetail.setHospitalid(hospital.getId());
            paymentdetail.setHospitalname(hospital.getName());
            paymentdetail.setCount(drugorderdetail.getAmount());
            paymentdetail.setFormat(drugorderdetail.getFormat());
            paymentdetail.setCreatedate(drugorder.getCreatedate());
            paymentdetailManage.deleteByPaymentdetail(paymentdetail);

            if (drugorder.getTotal() == 0) {
                drugorderdetailManage.delete(id);
                drugorderManage.delete(drugorder.getId());
            } else {
                drugorder.setMoney(drugorder.getMoney().subtract(drugorderdetail.getMoney()));
                drugorderdetailManage.delete(id);
                drugorderManage.update(drugorder);
            }
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
