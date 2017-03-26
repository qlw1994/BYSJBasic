package qlw.controller.admin.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.controller.BaseController;
import qlw.manage.PaymentManage;
import qlw.manage.PaymentdetailManage;
import qlw.model.Payment;
import qlw.model.Paymentdetail;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by wiki on 2017/3/23.
 */
@Controller
@RequestMapping(value = "/admin/payments")
public class PaymentController extends BaseController {
    @Autowired
    PaymentManage paymentManage;
    @Autowired
    PaymentdetailManage paymentdetailManage;

    /**
     * 支付订单列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listPayment(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, @RequestParam(value = "startDate", defaultValue = "") String startDate, @RequestParam(value = "endDate", defaultValue = "") String endDate, Long patientid, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            //Long patientid = (Long) request.getSession().getAttribute("patientid");
            Payment payment = new Payment();
            payment.setPatientid(patientid);
            result.put("total", paymentManage.count(startDate, endDate, payment));
            result.put("data", paymentManage.list(page, length, startDate, endDate, payment));
        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 支付订单管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(int pcode, int subcode, Long patientid, String patientname, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/payment");
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
     * 支付订单就诊人管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/patientChosen")
    public ModelAndView patientChosen(Long doctorid, String doctorname,String service, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/hospital/patient_chosen");
        request.setAttribute("service",service);
        request.getSession().setAttribute("doctorid", doctorid);
        request.getSession().setAttribute("doctorname", doctorname);
        return mv;
    }

    /**
     * 添加支付订单
     *
     * @param payment
     * @return
     */
    @RequestMapping(value = "newPayment")
    @ResponseBody
    public Map<String, Object> addPayment(Payment payment, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {
            payment.setDate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
            paymentManage.saveBackId(payment);
            List<Paymentdetail> paymentdetails = payment.getPaymentdetails();
            for (Paymentdetail d : paymentdetails) {
                d.setPaymentid(payment.getId());
                paymentdetailManage.save(d);
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
     * 支付订单查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "paymentInfo", method = RequestMethod.POST)
    @ResponseBody
    public Payment getPaymentInfo(Long id) {
        try {
            return paymentManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改支付订单
     *
     * @return
     */
    @RequestMapping(value = "modPayment", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updatePayment(Payment payment) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            paymentManage.update(payment);
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
     * 删除支付订单
     *
     * @return
     */
    @RequestMapping(value = "delPayment/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delPayment(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            paymentdetailManage.deleteByPaymentid(id);
            paymentManage.delete(id);
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
