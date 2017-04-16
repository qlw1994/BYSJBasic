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
 * Created by wiki on 2017/4/16.
 */
@Controller
@RequestMapping(value = "/admin/hospitalpays")
public class HospitalpayController extends BaseController{
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
    DoctorManage doctorManage;
    @Autowired
    PatientManage patientManage;
    @Autowired
    UserManage userManage;

    /**
     * 住院消费订单详情列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listHospitalpay(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            long hospitalizationid = Long.parseLong(request.getParameter("hospitalizationid"));
            result.put("total", hospitalpayManage.count(hospitalizationid));
            result.put("data", hospitalpayManage.list(page, length, hospitalizationid));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 住院消费订单详情管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(Integer pcode, Integer subcode, Long hospitalizationid, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/hospitalpay");
        request.getSession().setAttribute("hospitalizationid", hospitalizationid);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }

    /**
     * 添加住院消费订单详情
     *
     * @param hospitalpay
     * @return
     */
    @RequestMapping(value = "newHospitalpay")
    @ResponseBody
    public Map<String, Object> addHospitalpay(Hospitalpay hospitalpay, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            Long hospitalizationid = hospitalpay.getHospitalizationid();
            Hospitalization hospitalization = hospitalizationManage.getById(hospitalizationid);

            hospitalization.setMoney(hospitalization.getMoney().add(hospitalpay.getMoney()));
            hospitalizationManage.update(hospitalization);
            hospitalpayManage.saveBackId(hospitalpay);

            Hospital hospital = hospitalManage.getById(hospitalization.getHospitalid());
            Department department = departmentManage.getById(hospitalization.getDepartmentid());

            Patient patient = patientManage.getById(hospitalization.getPatientid());
            Users users = userManage.getUsersById(patient.getUid());
            Paymentdetail paymentdetail = new Paymentdetail();
            paymentdetail.setStatus(0);
            paymentdetail.setDepartmentid(hospitalization.getDepartmentid());
            paymentdetail.setDepartmentname(department.getName());
            paymentdetail.setHospitalid(hospital.getId());
            paymentdetail.setHospitalname(hospital.getName());
            paymentdetail.setCount(hospitalpay.getAmount());
            paymentdetail.setFormat(hospitalpay.getFormat());
            paymentdetail.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            paymentdetail.setPatientid(hospitalization.getPatientid());
            paymentdetail.setPatientname(patient.getName());
            paymentdetail.setUid(users.getId());
            paymentdetail.setUname(users.getName());
            paymentdetail.setMoney(hospitalpay.getMoney());

            paymentdetail.setProjecttype(1);//支付的是住院消费
            paymentdetail.setProjectid(hospitalpay.getId());// 住院消费详情编号
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
     * 住院消费订单详情查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "hospitalpayInfo", method = RequestMethod.POST)
    @ResponseBody
    public Hospitalpay getHospitalpayInfo(Long id) {
        try {
            return hospitalpayManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改住院消费订单详情
     *
     * @return
     */
    @RequestMapping(value = "modHospitalpay", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateHospitalpay(Hospitalpay hospitalpay, Object old_money) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            long hospitalizationid = hospitalpay.getHospitalizationid();
            Hospitalpay oddHospitalpay = new Hospitalpay();
            oddHospitalpay = hospitalpayManage.getById(hospitalpay.getId());
            BigDecimal oldmoney = BigDecimal.valueOf(Double.parseDouble((String) old_money));

            Hospitalization hospitalization = hospitalizationManage.getById(hospitalizationid);
            Hospital hospital = hospitalManage.getById(hospitalization.getHospitalid());
            Department department = departmentManage.getById(hospitalization.getDepartmentid());
            //Doctor doctor = doctorManage.getDoctorById(hospitalization.getDoctorid());
            Patient patient = patientManage.getById(hospitalization.getPatientid());
            Users users = userManage.getUsersById(patient.getUid());
            Paymentdetail paymentdetail = new Paymentdetail();
            paymentdetail.setMoney(oddHospitalpay.getMoney());
            paymentdetail.setPatientid(hospitalization.getPatientid());
            paymentdetail.setPatientname(patient.getName());
            paymentdetail.setUid(users.getId());
            paymentdetail.setUname(users.getName());
            paymentdetail.setDepartmentid(department.getId());
            paymentdetail.setDepartmentname(department.getName());
            //paymentdetail.setDoctorid(doctor.getId());
            //paymentdetail.setDoctorname(doctor.getName());
            paymentdetail.setHospitalid(hospital.getId());
            paymentdetail.setHospitalname(hospital.getName());
            paymentdetail.setCount(oddHospitalpay.getAmount());
            paymentdetail.setFormat(oddHospitalpay.getFormat());
            paymentdetail.setCreatedate(hospitalization.getStartdate());

            Paymentdetail newpaymentdetail = new Paymentdetail();
            newpaymentdetail.setCount(hospitalpay.getAmount());
            newpaymentdetail.setMoney(hospitalpay.getMoney());
            newpaymentdetail.setFormat(hospitalpay.getFormat());
            if (paymentdetailManage.updateByPaymentdetail(paymentdetail, newpaymentdetail) == -100) {
                rtnMsg = "该订单已经支付";
                rtnCode = ResultCode.ERROR;
            } else {
                hospitalization.setMoney((hospitalization.getMoney().subtract(oldmoney)).add(hospitalpay.getMoney()));
                hospitalizationManage.update(hospitalization);
                hospitalpayManage.update(hospitalpay);
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
     * 删除住院消费订单详情
     *
     * @return
     */
    @RequestMapping(value = "delHospitalpay/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delHospitalpay(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            Hospitalpay hospitalpay = hospitalpayManage.getById(id);
            Hospitalization hospitalization = hospitalizationManage.getById(hospitalpay.getHospitalizationid());
            //删除支付
            Hospital hospital = hospitalManage.getById(hospitalization.getHospitalid());
            Department department = departmentManage.getById(hospitalization.getDepartmentid());
            //Doctor doctor = doctorManage.getDoctorById(hospitalization.getDoctorid());
            Patient patient = patientManage.getById(hospitalization.getPatientid());
            Users users = userManage.getUsersById(patient.getUid());
            Paymentdetail paymentdetail = new Paymentdetail();
            paymentdetail.setMoney(hospitalpay.getMoney());
            paymentdetail.setPatientid(hospitalization.getPatientid());
            paymentdetail.setPatientname(patient.getName());
            paymentdetail.setUid(users.getId());
            paymentdetail.setUname(users.getName());
            paymentdetail.setDepartmentid(department.getId());
            paymentdetail.setDepartmentname(department.getName());
            //paymentdetail.setDoctorid(doctor.getId());
            //paymentdetail.setDoctorname(doctor.getName());
            paymentdetail.setHospitalid(hospital.getId());
            paymentdetail.setHospitalname(hospital.getName());
            paymentdetail.setCount(hospitalpay.getAmount());
            paymentdetail.setFormat(hospitalpay.getFormat());
            paymentdetail.setCreatedate(hospitalization.getStartdate());
            paymentdetailManage.deleteByPaymentdetail(paymentdetail);


                hospitalization.setMoney(hospitalization.getMoney().subtract(hospitalpay.getMoney()));
                hospitalpayManage.delete(id);
                hospitalizationManage.update(hospitalization);
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
