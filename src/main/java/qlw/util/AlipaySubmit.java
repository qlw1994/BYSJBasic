package qlw.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import qlw.config.AlipayConfig;
import qlw.sign.MD5;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* *
 *类名：AlipaySubmit
 *功能：支付宝各接口请求提交类
 *详细：构造支付宝各接口表单HTML文本，获取远程HTTP数据
 */

public class AlipaySubmit {
    
    /**
     * 支付宝提供给商户的服务接入网关URL(新)
     */
    private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
	
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	public static String buildRequestMysign(Map<String, String> sPara) {
    	String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(AlipayConfig.sign_type.equals("MD5") ) {
        	mysign = MD5.sign(prestr, AlipayConfig.key, AlipayConfig.input_charset);
        }
        return mysign;
    }
	
    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildRequestMysign(sPara);

        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", AlipayConfig.sign_type);

        return sPara;
    }

    /**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName) {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + ALIPAY_GATEWAY_NEW
                      + "_input_charset=" + AlipayConfig.input_charset + "\" method=\"" + strMethod
                      + "\">");
        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

        return sbHtml.toString();
    }
    
    /**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName,String charsest) {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"");
        sbHtml.append("http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        sbHtml.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        sbHtml.append("<head>");
        sbHtml.append("<title>支付宝 - 网上支付 安全快速！</title>");
        sbHtml.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        sbHtml.append("<meta http-equiv=\"x-ua-compatible\" content=\"ie=7\" />");
        sbHtml.append("<meta name=\"description\" content=\"中国最大的第三方电子支付服务提供商\" />");
        sbHtml.append("<meta name=\"keywords\" content=\"网上购物/网上支付/安全支付/安全购物/购物，安全/支付/支付宝,在线/付款,收款/网上,贸易/网上贸易\" />");
        
        sbHtml.append("<link rel=\"icon\" href=\"https://img.alipay.com:443/img/icon/favicon.ico\" type=\"image/x-icon\" />");
        sbHtml.append("<link rel=\"shortcut icon\" href=\"https://img.alipay.com:443/img/icon/favicon.ico\" type=\"image/x-icon\" />");
        sbHtml.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"https://img.alipay.com:443/assets/c/global/global_v1.css?t=20081119.css\" />");
        sbHtml.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"https://img.alipay.com:443/assets/c/sys/sys.tabs.css?t=20080709.css\" />");
        sbHtml.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"https://img.alipay.com:443/assets/c/typography/ot.old.css?t=20080709.css\" />");
        sbHtml.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"https://img.alipay.com:443/assets/c/typography/as.kt.css?t=20080709.css\" />");


        sbHtml.append("</head>");
        sbHtml.append("<body>");
        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + ALIPAY_GATEWAY_NEW
                      + "_input_charset=" + charsest + "\" method=\"" + strMethod
                      + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("</body></html>");
        
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

        return sbHtml.toString();
    }
    
    /**
     * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数
     * 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
     * @return 时间戳字符串
     * @throws IOException
     * @throws DocumentException
     * @throws MalformedURLException
     */
	public static String query_timestamp() throws MalformedURLException,
                                                        DocumentException, IOException {

        //构造访问query_timestamp接口的URL串
        String strUrl = ALIPAY_GATEWAY_NEW + "service=query_timestamp&partner=" + AlipayConfig.partner + "&_input_charset" +AlipayConfig.input_charset;
        StringBuffer result = new StringBuffer();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new URL(strUrl).openStream());

        List<Node> nodeList = doc.selectNodes("//alipay/*");

        for (Node node : nodeList) {
            // 截取部分不需要解析的信息
            if (node.getName().equals("is_success") && node.getText().equals("T")) {
                // 判断是否有成功标示
                List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
                for (Node node1 : nodeList1) {
                    result.append(node1.getText());
                }
            }
        }

        return result.toString();
    }
}
