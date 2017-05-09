package qlw.controller.users;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import qlw.config.AlipayConfigSandBox;
import qlw.controller.BaseController;
import qlw.manage.AlipayaccountManage;
import qlw.manage.PaymentdetailManage;
import qlw.model.Alipayaccount;
import qlw.model.Paymentdetail;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wiki on 2017/4/3.
 */
@Controller
@RequestMapping(value = "/user/paymentdetails")
public class Users_PaymentdetailController extends BaseController {
    @Autowired
    PaymentdetailManage paymentdetailManage;
    @Autowired
    AlipayaccountManage alipayaccountManage;
    protected static final Logger log = LoggerFactory.getLogger(Users_PaymentdetailController.class);
    private DateFormat format_yyyyMMddHHmmssSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 支付列表数据源
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listPaymentdetail(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "length", defaultValue = "20") Integer length, String startdate, String enddate, Long patientid, Long hospitalid, Long doctorid, Long departmentid, HttpServletRequest request, Integer payed) {
        Map<String, Object> result = new HashMap<>();
        try {
            Paymentdetail paymentdetail = new Paymentdetail();
            String dateStr = MyUtils.SIMPLE_DATE_FORMAT.format(new Date());
            //if (startdate == null) {
            //    startdate = dateStr;
            //}
            //if (enddate == null) {
            //    enddate = dateStr;
            //}
            paymentdetail.setDoctorid(doctorid);
            paymentdetail.setHospitalid(hospitalid);
            paymentdetail.setPatientid(patientid);
            paymentdetail.setDepartmentid(departmentid);
            if (payed != null && payed == 1) {
                result.put("total", paymentdetailManage.countpayed(startdate, enddate, paymentdetail));
                result.put("data", paymentdetailManage.listpayed(page, length, startdate, enddate, paymentdetail));
            } else {
                result.put("total", paymentdetailManage.count(startdate, enddate, paymentdetail));
                result.put("data", paymentdetailManage.list(page, length, startdate, enddate, paymentdetail));
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
    public ModelAndView View(Long patientid, String patientname, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("users/paymentdetail");
        request.getSession().setAttribute("patientname", patientname);
        request.getSession().setAttribute("patientid", patientid);
        mv.addObject("currentpage", 1);
        return mv;
    }


    /**
     * 支付管理首页跳转
     *
     * @return
     */
    @RequestMapping(value = "/indexpayed")
    public ModelAndView Viewpayed(Long patientid, String patientname, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("users/paymentdetail_payed");
        request.getSession().setAttribute("patientname", patientname);
        request.getSession().setAttribute("patientid", patientid);
        mv.addObject("currentpage", 1);
        return mv;
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
        data.put("invoicenumber", String.valueOf((new Date()).getTime()));
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
        ModelAndView mv = new ModelAndView("users/blank");

        return mv;
    }


    /**
     * 支付数据处理（RSA）
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
    public Map<String, Object> pay(Long hospitalid, Long uid, Long patientid, String uname, String patientname, String paytype, Double totalFee,
                                   String paymentdetailids, String subject, String invoicenumber, String test, HttpServletRequest request) {
        log.info("进入支付部分---------------->");
        Map<String, Object> regMsg = new HashMap<String, Object>();
        if (paytype.equals("1")) {
            log.info("支付宝支付------------------------------>");
            //获取医院的支付宝账户信息
            Alipayaccount alipayaccount = alipayaccountManage.getByName(null, hospitalid);
            AlipayConfigSandBox.APPID = alipayaccount.getAppid();
            AlipayConfigSandBox.RSA_PRIVATE_KEY = alipayaccount.getPrivatekey();
            String out_trade_no = "PAYN" + format_yyyyMMddHHmmssSSS.format(new Date()) + UUID.randomUUID().toString().substring(9, 13).toUpperCase();
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
            // 付款金额，必填
            String total_amount = String.valueOf(totalFee);
            // 超时时间 可空
            String timeout_express = "2m";
            // 销售产品码 必填
            String product_code = "QUICK_WAP_PAY";
            /**********************/
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
            //调用RSA签名方式
            AlipayClient client = new DefaultAlipayClient(AlipayConfigSandBox.URL, AlipayConfigSandBox.APPID, AlipayConfigSandBox.RSA_PRIVATE_KEY, AlipayConfigSandBox.FORMAT, AlipayConfigSandBox.CHARSET, AlipayConfigSandBox.ALIPAY_PUBLIC_KEY, AlipayConfigSandBox.SIGNTYPE);
            AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

            Map<String, String> user_patient = new HashMap<String, String>();
            user_patient.put("uid", String.valueOf(uid));
            user_patient.put("patientid", String.valueOf(patientid));
            user_patient.put("uname", uname);
            user_patient.put("patientname", patientname);
            String user_patient_data = JSONObject.toJSONString(mydata_map);
            // 封装请求支付信息
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            model.setOutTradeNo(out_trade_no);
            model.setSubject(mydata_encode);//业务参数
            model.setTotalAmount(total_amount);
            model.setBody(user_patient_data);
            model.setTimeoutExpress(timeout_express);
            model.setProductCode(product_code);
            model.setBody(subject);
            alipay_request.setBizModel(model);
            // 设置异步通知地址
            alipay_request.setNotifyUrl(AlipayConfigSandBox.user_notify_url);
            // 设置同步地址
            alipay_request.setReturnUrl(AlipayConfigSandBox.user_return_url);

            // form表单生产
            String form = "";

            try {
                // 调用SDK生成表单
                form = client.pageExecute(alipay_request).getBody();
            } catch (AlipayApiException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            log.info("[支付宝支付]报文参数：" + form);

            regMsg.put("code", ResultCode.SUCCESS);
            regMsg.put("data", form);

            return regMsg;
        } else if (paytype.equals("2"))

        {
            regMsg.put("code", ResultCode.ERROR);
            regMsg.put("message", "模块建设中");
            return regMsg;
        } else if (paytype.equals("3"))

        {
            regMsg.put("code", ResultCode.ERROR);
            regMsg.put("message", "模块建设中");
            return regMsg;
        } else

        {
            regMsg.put("code", ResultCode.PARAMETERS_NOTLEGAL);
            regMsg.put("message", "paytype参数不符合规则");
            return regMsg;
        }
    }

}
