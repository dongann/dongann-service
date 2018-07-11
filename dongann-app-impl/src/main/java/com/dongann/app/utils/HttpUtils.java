package com.dongann.app.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @FileName: HttpUtils
 * @Author: <a href="dongann@aliyun.cong">dongchang'an</a>.
 * @CreateTime: 2018/6/15 13:07
 * @Version: v1.0
 * @description: http的请求
 */
public class HttpUtils {
    private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url 发送请求的 URL
     * @return
     */
    public static String httpGetRequest(String url) {
        try {
            log.info("request:" + url);
            URL localURL = new URL(url);
            URLConnection connection = localURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpURLConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(4000);
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader reader = null;
            StringBuffer resultBuffer = new StringBuffer();
            String tempLine = null;
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is "
                        + httpURLConnection.getResponseCode());
            }
            try {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                }
            } finally {
                if (reader != null) {
                    reader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
//            log.info("response:"+resultBuffer.toString());
            return resultBuffer.toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return "";
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 或 "{\"id\":\"12345\"}" 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String httpPostRequest(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            log.info("request:" + url + param);
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(2000);
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//            log.info("http：请求返回"+in);
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
//            log.info("response:"+result);
        } catch (Exception e) {
            log.error("发送 POST 请求出现异常！" + e);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error(ex.getMessage());
                return "";
            }
        }
        return result;
    }

    /**
     * 发送POST请求
     *
     * @param url  请求地址
     * @param json 请求参数
     * @return 返回结果
     */
    public static String sendPostWithJson(String url, String json) {
        String responseContent = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(json,"UTF-8"));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            response.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseContent;
    }

    public static void main(String[] args) {
        String param = "{\"depositFee\":\"0.01\",\"method\":\"request\",\"recharges\":{\"rechargeAmount\":0.01,\"rechargeDesc\":\"保障金\",\"rechargeFrom\":\"微信\"},\"token\":\"e2a26fdb1787d80506125c04676c7b09\",\"version\":\"app_v1.0\"}";
        String url = "http://115.28.5.221:8080/api/alipay/request.json";
        String response = httpPostRequest(url, param);
        System.out.println(response);
    }
}
