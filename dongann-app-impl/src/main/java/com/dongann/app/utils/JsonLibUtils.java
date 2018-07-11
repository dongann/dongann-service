package com.dongann.app.utils;

import net.sf.ezmorph.Morpher;
import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.bean.BeanMorpher;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.*;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;
import net.sf.json.xml.XMLSerializer;

import javax.swing.text.Document;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @FileName: JsonLibUtils
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2016/6/21 20:40
 * @Version: v1.0
 * @description:
 */
public class JsonLibUtils {
    public static JsonConfig config = new JsonConfig();
    private static Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");
    static{
        //忽略循环，避免死循环
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        //处理Date日期转换
        config.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
            @Override
            public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d=(Date) arg1;
                return sdf.format(d);
            }
            @Override
            public Object processArrayValue(Object arg0, JsonConfig arg1) {
                return null;
            }
        });
    }

    /**
     * java object convert to json string
     * 可以用toString(1)来实现格式化，便于阅读
     */
    public static String pojo2json(Object obj){
        return JSONObject.fromObject(obj,config).toString();
    }

    /**
     * array、map、Javabean convert to json string
     */
    public static String object2json(Object obj){
        return JSONSerializer.toJSON(obj).toString();
    }

    /**
     * xml string convert to json string
     */
    public static String xml2json(String xmlString){
        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON json;
        json = xmlSerializer.read(xmlString);
        return json.toString();
    }

    /**
     * xml document convert to json string
     */
    public static String xml2json(Document xmlDocument){
        return xml2json(xmlDocument.toString());
    }

    /**
     * json string convert to javaBean
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> T json2pojo(String jsonStr,Class<T> clazz){
        JSONObject jsonObj = JSONObject.fromObject(jsonStr);
        T obj = (T) JSONObject.toBean(jsonObj, clazz);
        return obj;
    }

    /**
     * json string convert to map
     */
    public static Map<String,Object> json2map(String jsonStr){
        JSONObject jsonObj = JSONObject.fromObject(jsonStr);
        Map<String,Object> result = (Map<String, Object>) JSONObject.toBean(jsonObj, Map.class);
        return result;
    }

    /**
     * json string convert to map with javaBean
     */
    public static <T> Map<String,T> json2map(String jsonStr, Class<T> clazz){
        JSONObject jsonObj = JSONObject.fromObject(jsonStr);
        Map<String,T> map = new HashMap<String, T>();
        Map<String,T> result = (Map<String, T>) JSONObject.toBean(jsonObj, Map.class, map);
        MorpherRegistry morpherRegistry = JSONUtils.getMorpherRegistry();
        Morpher dynaMorpher = new BeanMorpher(clazz,morpherRegistry);
        morpherRegistry.registerMorpher(dynaMorpher);
        morpherRegistry.registerMorpher(new DateMorpher(new String[]{ "yyyy-MM-dd HH:mm:ss" }));
        for (Map.Entry<String,T> entry : result.entrySet()) {
            map.put(entry.getKey(), (T)morpherRegistry.morph(clazz, entry.getValue()));
        }
        return map;
    }

    /**
     * json string convert to array
     */
    public static Object[] json2arrays(String jsonString) {
        JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(jsonString);
//      JSONArray jsonArray = JSONArray.fromObject(jsonString);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);
        Object[] objArray = (Object[]) JSONSerializer.toJava(jsonArray,jsonConfig);
        return objArray;
    }

    /**
     * json string convert to list
     * @param <T>
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    public static <T> List<T> json2list(String jsonString, Class<T> pojoClass){
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        return JSONArray.toList(jsonArray, pojoClass);
    }

    /**
     * object convert to xml string
     */
    public static String obj2xml(Object obj){
        XMLSerializer xmlSerializer = new XMLSerializer();
        return xmlSerializer.write(JSONSerializer.toJSON(obj));
    }

    /**
     * json string convert to xml string
     */
    public static String json2xml(String jsonString){
        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setTypeHintsEnabled(true);//是否保留元素类型标识，默认true
        xmlSerializer.setElementName("e");//设置元素标签，默认e
        xmlSerializer.setArrayName("a");//设置数组标签，默认a
        xmlSerializer.setObjectName("o");//设置对象标签，默认o
        return xmlSerializer.write(JSONSerializer.toJSON(jsonString));
    }

    /**
     * 去除不规则json串中的回车符、换行符、空格符
     * @param str 请求json串
     * @return 处理后的json串
     */
    public static String beautifyJson(String str) {
        String beautifyJson = "";
        if (str!=null) {
            Matcher matcher = pattern.matcher(str);
            beautifyJson = matcher.replaceAll("");
        }
        return beautifyJson;
    }

    public static void main(String []args){
        System.out.print(xml2json("<?xml version=\"1.0\" encoding=\"gbk\" ?><response><code>03</code><message><desmobile>18661982627</desmobile><msgid>E2394916052119363900</msgid></message></response>"));
    }
}
