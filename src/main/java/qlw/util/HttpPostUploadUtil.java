package qlw.util;

/**
 * Created by qlw on 2017/1/14 .
 *
 * @Date 2017/1/14 0014 16:39
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpPostUploadUtil {
    private String uploadUrl;
    private String netServiceUrl;

    public HttpPostUploadUtil() {
    }

    public String getUploadUrl() {
        return this.uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getNetServiceUrl() {
        return this.netServiceUrl;
    }

    public void setNetServiceUrl(String netServiceUrl) {
        this.netServiceUrl = netServiceUrl;
    }

    public String formUpload(MultipartFile file, String... sizes) {
        String urlStr = this.uploadUrl;
        String res = "";
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------123821742118716";

        try {
            JSONObject reader;
            if(null != sizes && sizes.length > 0) {
                JSONObject e = new JSONObject();
                JSONArray out = new JSONArray();

                for(int endData = 0; endData < sizes.length; ++endData) {
                    String[] strBuf = sizes[endData].split("x");
                    if(null != strBuf && strBuf.length == 2) {
                        reader = new JSONObject();
                        reader.put("w", strBuf[0]);
                        reader.put("h", strBuf[1]);
                        out.add(reader);
                    }
                }

                e.put("sizes", out);
                urlStr = urlStr + "?imageinfo=" + URLEncoder.encode(e.toJSONString(), "utf-8");
            }

            URL var19 = new URL(urlStr);
            conn = (HttpURLConnection)var19.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            DataOutputStream var20 = new DataOutputStream(conn.getOutputStream());
            StringBuffer var23;
            if(file != null) {
                String var21 = "image/jpeg";
                var23 = new StringBuffer();
                var23.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                var23.append("Content-Disposition: form-data; name=\"" + file.getName() + "\"; filename=\"" + file.getName() + "\"\r\n");
                var23.append("Content-Type:" + var21 + "\r\n\r\n");
                var20.write(var23.toString().getBytes());
                DataInputStream var24 = new DataInputStream(file.getInputStream());
                boolean line = false;
                byte[] bufferOut = new byte[1024];

                int var26;
                while((var26 = var24.read(bufferOut)) != -1) {
                    var20.write(bufferOut, 0, var26);
                }

                var24.close();
            }

            byte[] var22 = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            var20.write(var22);
            var20.flush();
            var20.close();
            var23 = new StringBuffer();
            BufferedReader var25 = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String var27 = null;

            while((var27 = var25.readLine()) != null) {
                var23.append(var27).append("\n");
            }

            res = var23.toString();
            var25.close();
            reader = null;
        } catch (Exception var17) {
            System.out.println("发送POST请求出错。" + urlStr);
            var17.printStackTrace();
        } finally {
            if(conn != null) {
                conn.disconnect();
                conn = null;
            }

        }

        return res;
    }
}

