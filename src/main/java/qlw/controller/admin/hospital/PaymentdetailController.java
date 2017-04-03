package qlw.controller.admin.hospital;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qlw.config.AlipayConfig;
import qlw.controller.BaseController;
import qlw.manage.AlipayaccountManage;
import qlw.manage.PaymentdetailManage;
import qlw.model.Alipayaccount;
import qlw.model.Paymentdetail;
import qlw.util.AlipaySubmit;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wiki on 2017/3/26.
 */
@Controller
@RequestMapping(value = "/doctor/paymentdetails")
public class PaymentdetailController extends BaseController {
    @Autowired
    PaymentdetailManage paymentdetailManage;
    @Autowired
    AlipayaccountManage alipayaccountManage;
    protected static final Logger log = LoggerFactory.getLogger(PaymentdetailController.class);
    private DateFormat format_yyyyMMddHHmmssSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 支付列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listPaymentdetail(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, String starttime, String endtime, Long patientid, Long hospitalid, Long doctorid, Long departmentid, HttpServletRequest request, Integer payed) {
        Map<String, Object> result = new HashMap<>();
        try {
            Paymentdetail paymentdetail = new Paymentdetail();
            String dateStr = MyUtils.SIMPLE_DATE_FORMAT.format(new Date());
            if (starttime == null) {
                starttime = dateStr;
            }
            if (endtime == null) {
                endtime = dateStr;
            }
            paymentdetail.setDoctorid(doctorid);
            paymentdetail.setHospitalid(hospitalid);
            paymentdetail.setPatientid(patientid);
            paymentdetail.setDepartmentid(departmentid);
            if (payed != null && payed == 1) {
                result.put("total", paymentdetailManage.count(starttime, endtime, paymentdetail));
                result.put("data", paymentdetailManage.list(page, length, starttime, endtime, paymentdetail));
            } else {
                result.put("total", paymentdetailManage.count(starttime, endtime, paymentdetail));
                result.put("data", paymentdetailManage.list(page, length, starttime, endtime, paymentdetail));
            }

        } catch (Exception e) {
            result.put("total", 0);
            result.put("data", new ArrayList<>(0));
            e.printStackTrace();
        }
        result.put("code", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 支付管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView View(Long hospitalid, String hospitalname, int pcode, int subcode, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/account/paymentdetail");
        request.getSession().setAttribute("hospitalid", hospitalid);
        request.getSession().setAttribute("hospitalname", hospitalname);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }

    /**
     * 支付管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/hospitalChosenindex")
    public ModelAndView ViewhospitalChosenindex(Long patientid, String patientname, int pcode, int subcode, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("admin/account/payment_hospital_chosen");
        request.getSession().setAttribute("patientid", patientid);
        request.getSession().setAttribute("patientname", patientname);
        mv.addObject("pcode", pcode);
        mv.addObject("subcode", subcode);
        mv.addObject("currentpage", 1);
        return mv;
    }

    /**
     * 添加支付
     *
     * @param paymentdetail
     * @return
     */
    @RequestMapping(value = "newPaymentdetail")
    @ResponseBody
    public Map<String, Object> addPaymentdetail(Paymentdetail paymentdetail, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "添加成功";
        try {

            paymentdetail.setCreatedate(MyUtils.SIMPLE_DATE_FORMAT.format(new Date()));
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
     * 支付查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "paymentdetailInfo", method = RequestMethod.POST)
    @ResponseBody
    public Paymentdetail getPaymentdetailInfo(Long id) {
        try {
            return paymentdetailManage.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 修改支付
     *
     * @return
     */
    @RequestMapping(value = "modPaymentdetail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updatePaymentdetail(Paymentdetail paymentdetail) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "修改成功";
        try {
            paymentdetailManage.update(paymentdetail);
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
     * 删除支付
     *
     * @return
     */
    @RequestMapping(value = "delPaymentdetail/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delPaymentdetail(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "删除成功";
        try {
            paymentdetailManage.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "删除失败";
            rtnCode = ResultCode.ERROR;
        }
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

    /**
     * 预结算
     *
     * @param paymentdetailids
     * @return
     */
    @RequestMapping(value = "paymentBudget", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> paymentBudget(String paymentdetailids, String totalmoney, String invoicenumber) {
        Map<String, Object> result = new HashMap<>();
        Integer rtnCode = ResultCode.SUCCESS;
        String rtnMsg = "成功";
        Map<String, String> data = new HashMap<>();
        data.put("paymentdetailids", paymentdetailids);
        data.put("totalmoney", totalmoney);
        data.put("invoicenumber", invoicenumber);
        result.put("data", data);
        result.put("message", rtnMsg);
        result.put("code", rtnCode);
        return result;
    }

    /**
     * 诊间支付-主界面-空白页
     *
     * @return
     */
    @RequestMapping(value = "blank")
    public ModelAndView toBlankPage(Long hospitalid, Long patientid) {
        ModelAndView mv = new ModelAndView("admin/account/blank");

        return mv;
    }

    /**
     * 支付数据处理（MD5）
     *
     * @param hospitalid       医院UUID
     * @param totalFee         支付金额
     * @param paytype          支付方式，1-支付宝
     * @param paymentdetailids
     * @param subject          文字信息
     * @param invoicenumber    发票信息
     * @return
     */
    @RequestMapping(value = "pay")
    @ResponseBody
    public Map<String, Object> pay(Long hospitalid, Long uid, Long patientid, String uname, String patientname, Integer paytype, Double totalFee,
                                   String paymentdetailids, String subject, String invoicenumber, String test) {
        log.info("进入支付部分---------------->");
        Map<String, Object> regMsg = new HashMap<String, Object>();
        if (paytype.equals("1")) {
            log.info("支付宝支付------------------------------>");
            //获取医院的支付宝账户信息
            Alipayaccount alipayaccount = alipayaccountManage.getByName(null, hospitalid);
            AlipayConfig.partner = alipayaccount.getPid();
            AlipayConfig.key = alipayaccount.getCheckkey();
            String out_trade_no = "PAYN" + format_yyyyMMddHHmmssSSS.format(new Date()) + UUID.randomUUID().toString().substring(9, 13).toUpperCase();
            String total_fee = totalFee.toString();//"0.01" totalFee.toString();

            Map<String, String> mydata_map = new HashMap<String, String>();
            mydata_map.put("uid", String.valueOf(uid));
            mydata_map.put("patientid", String.valueOf(patientid));
            mydata_map.put("uname", uname);
            mydata_map.put("patientname", patientname);
            mydata_map.put("paymentdetailids", paymentdetailids);//识别序号
            mydata_map.put("paytype", String.valueOf(paytype));
            mydata_map.put("paynumber", out_trade_no);//交易的流水号
            mydata_map.put("invoicenumber", invoicenumber);//发票号码
            log.info("[支付宝支付]body参数源数据：" + mydata_map);
            String mydata = JSONObject.toJSONString(mydata_map);
            String mydata_encode = qlw.sign.Base64.encode(mydata.getBytes());
            log.info("[支付宝支付]body参数编码后：" + mydata_encode);

            Map<String, String> sParaTemp = new HashMap<String, String>();
            sParaTemp.put("service", "alipay.wap.create.direct.pay.by.user");
            sParaTemp.put("partner", AlipayConfig.partner);
            sParaTemp.put("seller_id", AlipayConfig.partner);
            sParaTemp.put("_input_charset", "utf-8");
            sParaTemp.put("payment_type", AlipayConfig.payment_type);
            sParaTemp.put("notify_url", AlipayConfig.notify_url);
            if (test != null && "1".equals(test)) {
                sParaTemp.put("return_url", "");
            } else {
                sParaTemp.put("return_url", AlipayConfig.return_url);
            }
            sParaTemp.put("out_trade_no", out_trade_no);
            sParaTemp.put("subject", subject);
            sParaTemp.put("total_fee", total_fee);
            sParaTemp.put("show_url", "");
            sParaTemp.put("body", mydata_encode);
            log.info("[支付宝支付]报文参数：" + sParaTemp);
            String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认", "utf-8");
            log.info("[支付宝支付]响应：" + sHtmlText + "\n\n------------------------------------>结束");

            regMsg.put("code", ResultCode.SUCCESS);
            regMsg.put("data", sHtmlText);

            return regMsg;
        } else if (paytype.equals("2")) {
            regMsg.put("code", ResultCode.ERROR);
            regMsg.put("message", "模块建设中");
            return regMsg;
        } else if (paytype.equals("3")) {
            regMsg.put("code", ResultCode.ERROR);
            regMsg.put("message", "模块建设中");
            return regMsg;
        } else {
            regMsg.put("code", ResultCode.PARAMETERS_NOTLEGAL);
            regMsg.put("message", "paytype参数不符合规则");
            return regMsg;
        }
    }

}
