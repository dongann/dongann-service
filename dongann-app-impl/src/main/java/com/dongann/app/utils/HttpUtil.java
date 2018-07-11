package com.dongann.app.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpUtil {
    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * http post
     *
     * @param url
     * @param requestHeaderProperty request请求属性
     * @param param                 参数
     * @param charset               字符集
     * @return
     */
    public static String httpPost(String url, Map<String, String> requestHeaderProperty, String param, String charset) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer("");
        String tip = "http url:" + url + "; http request:" + param;
        log.info(tip);
        try {
            if (StringUtils.isEmpty(charset)) {
                charset = "utf-8";
            }
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            int timeout = 5 * 1000;
            //建立连接的超时时间
            conn.setConnectTimeout(2 * 1000);
            //传递数据的超时时间
            conn.setReadTimeout(timeout);
            // 设置通用的请求属性
            if (requestHeaderProperty != null) {
                for (Map.Entry<String, String> property : requestHeaderProperty.entrySet()) {
                    conn.setRequestProperty(property.getKey(), property.getValue());
                }
            } else {
                conn.setRequestProperty("Content-Type", "text/plain");
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), charset));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            //result = new StringBuffer("");
            //log.error("发送POST请求出现异常" + e);
            throw new Exception("发送POST请求出现异常", e);
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
                log.error("http post IO 关闭异常", ex);
                //result = new StringBuffer("");
                //throw new Exception("http post IO 关闭异常", ex);
            }
        }
        return result.toString();
    }

    /**
     * http Post请求
     *
     * @param url
     * @return
     */
    public static String httpPost(String url, Map<String, Object> parametersMap) throws Exception {
        String tip = "http url:" + url + "; http request:" + parametersMap;
        log.info(tip);
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(2000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(5000).build();
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
            if (parametersMap != null) {
                for (String key : parametersMap.keySet()) {
                    qparams.add(new BasicNameValuePair(key, parametersMap.get(key).toString()));
                }
            }
            UrlEncodedFormEntity params = new UrlEncodedFormEntity(qparams, "UTF-8");
            httpPost.setEntity(params);

            HttpResponse response = httpClient.execute(httpPost);
            if (null != response) {
                HttpEntity httpEntity = response.getEntity();
                if (null != httpEntity) {
                    result = EntityUtils.toString(httpEntity, "UTF-8");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info(tip + "; http return:" + result.toString());
        return result;

    }

    public static void main(String[] args) throws Exception {
        Map pmap = new HashMap();
        pmap.put("method", "saveAccountsDemo");
        pmap.put("token", "LJFNC");
        pmap.put("plat", "农特");
        pmap.put("accountName", "农特测试账号");
        pmap.put("balance", 2000);
        String ps = JSON.toJSONString(pmap);
        String rst = httpPost("http://127.0.0.1:8080/api/saveAccountsDemo.json", null, ps, null);
        JSONObject rj = JSON.parseObject(rst);
        System.out.println(rj);


    }

}
