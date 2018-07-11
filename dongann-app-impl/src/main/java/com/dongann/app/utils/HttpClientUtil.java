package com.dongann.app.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClientUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    public HttpClientUtil() {
    }

    public static String doPut(String url, Map<String, String> map, String charset, Map<String, String> headers, boolean isJSON) {
        log.info("[httpClientUtil][doPost]请求信息:" + url);
        if (StringUtil.isEmpty(charset)) {
            charset = "utf-8";
        }

        HttpClient httpClient = null;
        HttpPut httpPut = null;
        String result = null;
        Object var8 = null;

        try {
            httpClient = HttpClients.createDefault();
            httpPut = new HttpPut(url);
            if (map != null) {
                if (isJSON) {
                    StringEntity entity = new StringEntity(JSON.toJSONString(map), Charset.forName(charset));
                    httpPut.setEntity(entity);
                } else {
                    List<NameValuePair> list = new ArrayList();
                    Iterator iterator = map.entrySet().iterator();

                    while(iterator.hasNext()) {
                        Entry<String, String> elem = (Entry)iterator.next();
                        list.add(new BasicNameValuePair((String)elem.getKey(), (String)elem.getValue()));
                    }

                    if (list.size() > 0) {
                        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                        httpPut.setEntity(entity);
                    }
                }
            }

            if (headers != null) {
                Iterator var14 = headers.entrySet().iterator();

                while(var14.hasNext()) {
                    Entry<String, String> headerMap = (Entry)var14.next();
                    httpPut.setHeader((String)headerMap.getKey(), (String)headerMap.getValue());
                }
            }

            HttpResponse response = httpClient.execute(httpPut);
            response.getAllHeaders();
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }

            log.info("[httpClientUtil][doPost]返回信息:" + result);
        } catch (Exception var12) {
            var12.printStackTrace();
        }

        return result;
    }

    public static String doGet(String url, Map<String, String> map, String charset, Map<String, String> headers) {
        log.info("[httpClientUtil][doPost]请求信息:" + url);
        if (StringUtil.isEmpty(charset)) {
            charset = "utf-8";
        }

        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        Object var7 = null;

        try {
            httpClient = HttpClients.createDefault();
            httpGet = new HttpGet(url);
            List<NameValuePair> list = new ArrayList();
            Iterator iterator;
            Entry headerMap;
            if (map != null) {
                iterator = map.entrySet().iterator();

                while(iterator.hasNext()) {
                    headerMap = (Entry)iterator.next();
                    list.add(new BasicNameValuePair((String)headerMap.getKey(), (String)headerMap.getValue()));
                }
            }

            if (headers != null) {
                iterator = headers.entrySet().iterator();

                while(iterator.hasNext()) {
                    headerMap = (Entry)iterator.next();
                    httpGet.setHeader((String)headerMap.getKey(), (String)headerMap.getValue());
                }
            }

            String str = EntityUtils.toString(new UrlEncodedFormEntity(list), charset);
            httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + str));
            HttpResponse response = httpClient.execute(httpGet);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }

            log.info("[httpClientUtil][doPost]返回信息:" + result);
        } catch (Exception var12) {
            var12.printStackTrace();
        }

        return result;
    }

    public static String doPost(String url, Map<String, String> map, String charset, Map<String, String> headers, boolean isJSON) {
        log.info("[httpClientUtil][doPost]请求信息:" + url);
        if (StringUtil.isEmpty(charset)) {
            charset = "utf-8";
        }

        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;

        try {
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            if (map != null) {
                if (isJSON) {
                    StringEntity entity = new StringEntity(JSON.toJSONString(map), Charset.forName(charset));
                    httpPost.setEntity(entity);
                } else {
                    List<NameValuePair> list = new ArrayList();
                    Iterator iterator = map.entrySet().iterator();

                    while(iterator.hasNext()) {
                        Entry<String, String> elem = (Entry)iterator.next();
                        list.add(new BasicNameValuePair((String)elem.getKey(), (String)elem.getValue()));
                    }

                    if (list.size() > 0) {
                        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                        httpPost.setEntity(entity);
                    }
                }
            }

            if (headers != null) {
                Iterator var13 = headers.entrySet().iterator();

                while(var13.hasNext()) {
                    Entry<String, String> headerMap = (Entry)var13.next();
                    httpPost.setHeader((String)headerMap.getKey(), (String)headerMap.getValue());
                }
            }

            HttpResponse response = httpClient.execute(httpPost);
            response.getAllHeaders();
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }

            log.info("[httpClientUtil][doPost]返回信息:" + result);
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return result;
    }

    public static String doPost(String url, Map<String, String> map, String charset) {
        log.info("[httpClientUtil][doPost]请求信息:" + url);
        if (StringUtil.isEmpty(charset)) {
            charset = "utf-8";
        }

        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;

        try {
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            if (map != null) {
                List<NameValuePair> list = new ArrayList();
                Iterator iterator = map.entrySet().iterator();

                while(iterator.hasNext()) {
                    Entry<String, String> elem = (Entry)iterator.next();
                    list.add(new BasicNameValuePair((String)elem.getKey(), (String)elem.getValue()));
                }

                if (list.size() > 0) {
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                    httpPost.setEntity(entity);
                }
            }

            HttpResponse response = httpClient.execute(httpPost);
            response.getAllHeaders();
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }

            log.info("[httpClientUtil][doPost]返回信息:" + result);
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return result;
    }

    public static String doPost(String url, String json) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;

        try {
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(json, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "utf-8");
                }
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return result;
    }

    public static String get(String url, List<NameValuePair> params) {
        String body = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpGet httpget = new HttpGet(url);
            String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
            httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
            HttpResponse httpresponse = httpClient.execute(httpget);
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity);
            if (entity != null) {
                entity.consumeContent();
            }
        } catch (ParseException var8) {
            var8.printStackTrace();
        } catch (UnsupportedEncodingException var9) {
            var9.printStackTrace();
        } catch (IOException var10) {
            var10.printStackTrace();
        } catch (URISyntaxException var11) {
            var11.printStackTrace();
        }

        return body;
    }

    public static String doGet(String url, Map<String, String> paramsMap) {
        String body = null;
        DefaultHttpClient httpClient = new DefaultHttpClient();

        try {
            HttpGet httpget = new HttpGet(url);
            httpget.setURI(new URI(httpget.getURI().toString()));
            if (paramsMap != null && !paramsMap.isEmpty()) {
                List<NameValuePair> list = new ArrayList();
                Iterator iterator = paramsMap.entrySet().iterator();

                while(iterator.hasNext()) {
                    Entry<String, String> elem = (Entry)iterator.next();
                    list.add(new BasicNameValuePair((String)elem.getKey(), (String)elem.getValue()));
                }

                String str = EntityUtils.toString(new UrlEncodedFormEntity(list, "utf-8"));
                httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
            }

            HttpResponse httpresponse = httpClient.execute(httpget);
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity);
            if (entity != null) {
                entity.consumeContent();
            }
        } catch (ParseException var8) {
            var8.printStackTrace();
        } catch (UnsupportedEncodingException var9) {
            var9.printStackTrace();
        } catch (IOException var10) {
            var10.printStackTrace();
        } catch (URISyntaxException var11) {
            var11.printStackTrace();
        }

        return body;
    }
}
