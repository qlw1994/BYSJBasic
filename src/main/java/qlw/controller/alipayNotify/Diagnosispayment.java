package qlw.controller.alipayNotify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import qlw.config.AlipayConfigSandBox;
import qlw.controller.BaseController;
import qlw.manage.PaymentdetailManage;
import qlw.model.Paymentdetail;
import qlw.util.Base64Utils;
import qlw.util.MyUtils;
import qlw.util.ResultCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/22 0022.
 *
 * @Date 2016/8/22 0022 8:40
 */
@Controller
@RequestMapping(value = "diagnosispay")
public class Diagnosispayment extends BaseController {
    @Autowired
    PaymentdetailManage paymentdetailManage;
    protected static final Logger log = LoggerFactory.getLogger(Diagnosispayment.class);

    /**
     * 支付确认
     *
     * @return
     */
    @RequestMapping(value = "paymentConfirm")
    @ResponseBody
    public Map<String, Object> paymentConfirm(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
        Map<String, Object> properties = request.getParameterMap();
        String out = "fail";
        Map<String, String> map = new HashMap<String, String>();
        log.info("================alipay notify====================");
        for (String key : properties.keySet()) {
            map.put(key.toString(), request.getParameter(key.toString()).toString());
            log.info(key + ":" + request.getParameter(key));
        }
        log.info("================alipay notify  end====================");
        String trade_status = map.get("trade_status");
        log.info("trade_status:" + trade_status);
        log.info("map:" + map);
        try {
            if (AlipaySignature.rsaCheckV1(map, AlipayConfigSandBox.ALIPAY_PUBLIC_KEY, AlipayConfigSandBox.CHARSET, "RSA")) {
                //验证成功
                log.info("================alipay notify  sign验证成功===================");
                if (trade_status != null && (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS"))) {
                    log.info("=============notify  数据解密 start =================");
                    //数据解密
                    String passback_params = request.getParameter("passback_params");
                    try {
                        String mydata_decode = Base64Utils.getFromBase64(passback_params);
                        JSON mydata_temp = JSONObject.parseObject(mydata_decode);
                        Map<String, String> mydata_map = JSONObject.toJavaObject(mydata_temp, Map.class);


                        for (String key : mydata_map.keySet()) {
                            log.info(key + ":" + mydata_map.get(key).toString());
                        }
                        log.info("=============notify  数据解密 end ===================");
                        //数据提取
                        String paymentdetailids = mydata_map.get("paymentdetailids");
                        int paytype = Integer.parseInt(mydata_map.get("paytype"));
                        Long patientid = Long.parseLong(mydata_map.get("patientid"));
                        String patientname = mydata_map.get("patientname");
                        Long uid = Long.parseLong(mydata_map.get("uid"));
                        String uname = mydata_map.get("uname");
                        String paynumber = mydata_map.get("paynumber");
                        String invoicenumber = mydata_map.get("invoicenumber");
                        //Double totalmoney = Double.parseDouble(request.getParameter("total_fee"));


                        //request中增加数据
                        request.setAttribute("paytype", paytype);
                        request.setAttribute("patientid", patientid);
                        request.setAttribute("patientname", patientname);
                        request.setAttribute("uid", uid);
                        request.setAttribute("uname", uname);
                        request.setAttribute("paymentdetailids", paymentdetailids);
                        request.setAttribute("paynumber", paynumber);
                        //request.setAttribute("totalmoney", totalmoney);
                        request.setAttribute("invoicenumber", invoicenumber);

                        log.info("---paymentdetailids------------"+paymentdetailids);
                        boolean resflag = paymentdetailManage.paymentConfirm(paymentdetailids, paytype,invoicenumber,  paynumber);
                        log.info("paymentConfirm-----------resflag：" + resflag + "--------------------");
                        if (resflag) {
                            out = "success";
                        }
                    } catch (Exception e) {
                        log.info("paymentConfirm----------请求出错---------------------");
                        log.info(Throwables.getStackTraceAsString(e));
                        res.put("code", ResultCode.ERROR);
                        res.put("message", "notify success！！！！,Exception");
                    }
                } else {
                    log.info("not callback..............................");
                }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //通知支付宝页面
        try {
            log.info("-----------------------notify success！！！！--------------------------");
            response.getOutputStream().print(out);
            res.put("code", ResultCode.SUCCESS);
            res.put("message", "notify success！！！！");
        } catch (IOException e) {
            log.info(Throwables.getStackTraceAsString(e));
            res.put("code", ResultCode.ERROR);
            res.put("message", "notify success！！！！,IOException");
        }
        return res;
    }

    @RequestMapping(value = "return_url")
    public ModelAndView return_url(HttpServletRequest request) {
        Map model = MyUtils.getParameterMap(request);
        ModelAndView modelAndView = null;
        Map<String, Object> properties = request.getParameterMap();

        Map<String, String> map = new HashMap<String, String>();
        log.info("================alipay return_url====================");
        for (String key : properties.keySet()) {
            map.put(key.toString(), request.getParameter(key.toString()).toString());
            log.info(key + ":" + request.getParameter(key));
        }
        log.info("================alipay return_url  end====================");
        String trade_status = map.get("trade_status");
        log.info("trade_status:" + trade_status);
        log.info("map:" + map);
        try {
            if (AlipaySignature.rsaCheckV1(map, AlipayConfigSandBox.ALIPAY_PUBLIC_KEY, AlipayConfigSandBox.CHARSET, "RSA")) {
                //验证成功
                log.info("===========alipay return 验证成功==========");
                //if (trade_status != null && (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS"))) {
                //数据解析
                String paynumber = request.getParameter("out_trade_no");
                //JSON mydata_temp = JSONObject.parseObject(user_patient_data);
                //Map<String, String> mydata_map = JSONObject.toJavaObject(mydata_temp, Map.class);
                //
                //log.info("=============  数据解析 start =================");
                //for (String key : mydata_map.keySet()) {
                //    log.info(key + ":" + mydata_map.get(key).toString());
                //}
                //log.info("=============  数据解析 end ===================");
                Paymentdetail paymentdetail = paymentdetailManage.getByPaynumber(paynumber);

                //数据提取
                Long patientid =paymentdetail.getPatientid();
                String patientname = paymentdetail.getPatientname();
                Long uid =paymentdetail.getUid();
                String uname =paymentdetail.getUname();

                //request中增加数据
                String tempPath = request.getRequestURL().toString();
                String basePath = "http://" + tempPath.split("/")[2];
                log.info("--------------------basepath-------:");
                log.info(tempPath);
                log.info(tempPath.split("/")[2]);
                model.put("basePath", basePath);
                model.put("uid", uid);
                model.put("uname", uname);
                model.put("patientid", patientid);
                model.put("patientname", patientname);
                modelAndView = new ModelAndView("admin/account/paysuccess", model);
                //}
            } else {
                log.info("===========alipay return 验证失败==========");


            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 支付确认
     *
     * @return
     */
    @RequestMapping(value = "user_paymentConfirm")
    @ResponseBody
    public Map<String, Object> user_paymentConfirm(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
        Map<String, Object> properties = request.getParameterMap();
        String out = "fail";
        Map<String, String> map = new HashMap<String, String>();
        log.info("================alipay notify====================");
        for (String key : properties.keySet()) {
            map.put(key.toString(), request.getParameter(key.toString()).toString());
            log.info(key + ":" + request.getParameter(key));
        }
        log.info("================alipay notify  end====================");
        String trade_status = map.get("trade_status");
        log.info("trade_status:" + trade_status);
        log.info("map:" + map);
        try {
            if (AlipaySignature.rsaCheckV1(map, AlipayConfigSandBox.ALIPAY_PUBLIC_KEY, AlipayConfigSandBox.CHARSET, "RSA")) {
                //验证成功
                log.info("================alipay notify  sign验证成功===================");
                if (trade_status != null && (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS"))) {
                    log.info("callback..............................");
                    //数据解密
                    String passback_params = request.getParameter("passback_params");
                    String mydata_decode = Base64Utils.getFromBase64(passback_params);
                    JSON mydata_temp = JSONObject.parseObject(mydata_decode);
                    Map<String, String> mydata_map = JSONObject.toJavaObject(mydata_temp, Map.class);
                    try {
                        log.info("=============notify  数据解密 start =================");
                        for (String key : mydata_map.keySet()) {
                            log.info(key + ":" + mydata_map.get(key).toString());
                        }
                        log.info("=============notify  数据解密 end ===================");
                        //数据提取
                        String paymentdetailids = mydata_map.get("paymentdetailids");
                        int paytype = Integer.parseInt(mydata_map.get("paytype"));
                        Long patientid = Long.parseLong(mydata_map.get("patientid"));
                        String patientname = mydata_map.get("patientname");
                        Long uid = Long.parseLong(mydata_map.get("uid"));
                        String uname = mydata_map.get("uname");
                        String paynumber = mydata_map.get("paynumber");
                        String invoicenumber = mydata_map.get("invoicenumber");
                        //Double totalmoney = Double.parseDouble(request.getParameter("total_fee"));


                        //request中增加数据
                        request.setAttribute("paytype", paytype);
                        request.setAttribute("patientid", patientid);
                        request.setAttribute("patientname", patientname);
                        request.setAttribute("uid", uid);
                        request.setAttribute("uname", uname);
                        request.setAttribute("paymentdetailids", paymentdetailids);
                        request.setAttribute("paynumber", paynumber);
                        //request.setAttribute("totalmoney", totalmoney);
                        request.setAttribute("invoicenumber", invoicenumber);

                        boolean resflag = paymentdetailManage.paymentConfirm(paymentdetailids, paytype,  invoicenumber,paynumber);
                        log.info("paymentConfirm-----------resflag：" + resflag + "--------------------");
                        if (resflag) {
                            out = "success";
                        }
                    } catch (Exception e) {
                        log.info("paymentConfirm----------请求出错---------------------");
                        log.info(Throwables.getStackTraceAsString(e));
                        res.put("code", ResultCode.ERROR);
                        res.put("message", "notify success！！！！,Exception");
                    }
                } else {
                    log.info("not callback..............................");
                }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //通知支付宝页面
        try {
            log.info("-----------------------notify success！！！！--------------------------");
            response.getOutputStream().print(out);
            res.put("code", ResultCode.SUCCESS);
            res.put("message", "notify success！！！！");
        } catch (IOException e) {
            log.info(Throwables.getStackTraceAsString(e));
            res.put("code", ResultCode.ERROR);
            res.put("message", "notify success！！！！,IOException");
        }
        return res;
    }

    @RequestMapping(value = "user_return_url")
    public ModelAndView user_return_url(HttpServletRequest request) {
        Map model = MyUtils.getParameterMap(request);
        ModelAndView modelAndView = null;
        Map<String, Object> properties = request.getParameterMap();

        Map<String, String> map = new HashMap<String, String>();
        log.info("================alipay return_url====================");
        for (String key : properties.keySet()) {
            map.put(key.toString(), request.getParameter(key.toString()).toString());
            log.info(key + ":" + request.getParameter(key));
        }
        log.info("================alipay return_url  end====================");
        String trade_status = map.get("trade_status");
        log.info("trade_status:" + trade_status);
        log.info("map:" + map);
        try {
            if (AlipaySignature.rsaCheckV1(map, AlipayConfigSandBox.ALIPAY_PUBLIC_KEY, AlipayConfigSandBox.CHARSET, "RSA")) {
                //验证成功
                log.info("===========alipay return 验证成功==========");
                //if (trade_status != null && (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS"))) {
                //数据解析
                String paynumber = request.getParameter("out_trade_no");
                //JSON mydata_temp = JSONObject.parseObject(user_patient_data);
                //Map<String, String> mydata_map = JSONObject.toJavaObject(mydata_temp, Map.class);
                //
                //log.info("=============  数据解析 start =================");
                //for (String key : mydata_map.keySet()) {
                //    log.info(key + ":" + mydata_map.get(key).toString());
                //}
                //log.info("=============  数据解析 end ===================");
                Paymentdetail paymentdetail = paymentdetailManage.getByPaynumber(paynumber);

                //数据提取
                Long patientid =paymentdetail.getPatientid();
                String patientname = paymentdetail.getPatientname();
                Long uid =paymentdetail.getUid();
                String uname =paymentdetail.getUname();
                //request中增加数据
                String tempPath = request.getRequestURL().toString();
                String basePath = "http://" + tempPath.split("/")[2];
                log.info("--------------------basepath-------:");
                log.info(tempPath);
                log.info(tempPath.split("/")[2]);
                model.put("basePath", basePath);
                model.put("uid", uid);
                model.put("uname", uname);
                model.put("patientid", patientid);
                model.put("patientname", patientname);
                modelAndView = new ModelAndView("users/paysuccess", model);
                //}
            } else {
                log.info("===========alipay return 验证失败==========");


            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }
}
