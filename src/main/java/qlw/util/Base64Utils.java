package qlw.util;

/**
 * Created by wiki on 2017/3/27.
 */
import java.io.UnsupportedEncodingException;

public class Base64Utils {
    public Base64Utils() {
    }

    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;

        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
        }

        if(b != null) {
            s = (new qlw.util.BASE64Encoder()).encode(b);
        }

        return s;
    }

    public static String getFromBase64(String s) {
        Object b = null;
        String result = null;
        if(s != null) {
            qlw.util.BASE64Decoder decoder = new BASE64Decoder();

            try {
                byte[] b1 = decoder.decodeBuffer(s);
                result = new String(b1, "utf-8");
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }

        return result;
    }
}