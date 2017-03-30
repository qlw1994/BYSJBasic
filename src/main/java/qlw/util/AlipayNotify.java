package qlw.util;

/**
 * Created by Administrator on 2016/8/10 0010.
 *
 * @Date 2016/8/10 0010 15:29
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qlw.config.AlipayConfig;
import qlw.sign.MD5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/* *
 *类名：AlipayNotify
 *功能：支付宝通知处理类
 *详细：处理支付宝各接口通知返回
 *版本：3.3
 *日期：2012-08-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。并非一定要使用该代码
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考

 *************************注意*************************
 *调试通知返回时，可查看或改写log日志的写入TXT里的数据，来检查通知返回是否正常
 */
public class AlipayNotify {
    protected static final Logger log = LoggerFactory.getLogger(AlipayNotify.class);

    /**
     * 支付宝消息验证地址
     */
    private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

    /**
     * 验证消息是否是支付宝发出的合法消息
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verify(Map<String, String> params) {
    	log.info("开始Verify....");
        //判断responsetTxt是否为true，isSign是否为true
        //responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
        //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
        String responseTxt = "false";
        String notify_id = params.get("notify_id");
        if(notify_id != null) {
            log.info("Verify--获取notify_id参数值，notify_id = " + notify_id);
            responseTxt = verifyResponse(notify_id);
            log.info("Verify--获取获取远程服务器ATN结果[responseTxt:" + responseTxt + "]");
        }
        String sign = "";
        if(params.get("sign") != null) {sign = params.get("sign");}
        log.info("Verify--获取校验码[sign]，sign = " + sign);
        boolean isSign = getSignVeryfy(params, sign);
        log.info("Verify--获取验签结果[isSign:" + isSign + "]");

        //写日志记录（若要调试，请取消下面两行注释）
        //String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign + "\n 返回回来的参数：" + AlipayCore.createLinkString(params);
        //AlipayCore.logResult(sWord);

        if (isSign && responseTxt.equals("true")) {
        	log.info("Verify结束，结果：" + true);
            return true;
        } else {
        	log.info("Verify结束，结果：" + false);
            return false;
        }
    }

    /**
     * 根据反馈回来的信息，生成签名结果
     * @param Params 通知返回来的参数数组
     * @param sign 比对的签名结果
     * @return 生成的签名结果
     */
    private static boolean getSignVeryfy(Map<String, String> Params, String sign) {
        //过滤空值、sign与sign_type参数
        Map<String, String> sParaNew = AlipayCore.paraFilter(Params);

    	log.info("验签，用于校验的原始内容[" + sParaNew + "]");
        //获取待签名字符串
        String preSignStr = AlipayCore.createLinkString(sParaNew);
    	log.info("验签，用于校验的字符串：" + preSignStr);
        //获得签名验证结果
        boolean isSign = false;
        if(AlipayConfig.sign_type.equals("MD5") ) {
            isSign = MD5.verify(preSignStr, sign, AlipayConfig.key, AlipayConfig.input_charset);
        }
        return isSign;
    }

    /**
     * 获取远程服务器ATN结果,验证返回URL
     * @param notify_id 通知校验ID
     * @return 服务器ATN结果
     * 验证结果集：
     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空
     * true 返回正确信息
     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
     */
    private static String verifyResponse(String notify_id) {
        //获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求

        String partner = AlipayConfig.partner;
        String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;

        return checkUrl(veryfy_url);
    }

    /**
     * 获取远程服务器ATN结果
     * @param urlvalue 指定URL路径地址
     * @return 服务器ATN结果
     * 验证结果集：
     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空
     * true 返回正确信息
     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
     */
    private static String checkUrl(String urlvalue) {
        String inputLine = "";

        try {
            URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
                    .getInputStream()));
            inputLine = in.readLine().toString();
        } catch (Exception e) {
            e.printStackTrace();
            inputLine = "";
        }

        return inputLine;
    }
}

