package com.dongann.app.utils;

import com.alibaba.fastjson.JSONObject;
import com.dongann.app.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过传入的功能码找到需要校验的数据<b>(mapping_keys.properties)</b>,对需要校验的数据进行正则表达式<b>
 * (mapping_regex.properties)</b>校验
 *
 */
public class ValidateUtil {
    private static Logger logger = LoggerFactory.getLogger(ValidateUtil.class);

    public static void check(String service, JSONObject req) throws BusinessException {
        // 功能码不能为空
        if (StringUtil.isEmpty(service)) {
            return;
        }
        // 找到这个服务需要校验哪些字段
        String keys = SpringUtil.getProperty("Service." + service);
        if (StringUtil.isEmpty(keys)) {
            logger.info(String.format("Service %s found no check info", service));
            return;
        }
        // 对每个key进行正则表达式校验
        String[] chkKeys = keys.split(",");
        for (String key : chkKeys) {
            doCheck(key, req);
        }
    }

    /**
     * delegater
     * 
     * @param key
     * @throws Exception
     */
    protected static void doCheck(String key, JSONObject in) throws BusinessException {
        if (StringUtil.isEmpty(key)) {
            return;
        }
        if (isOptional(key)) {
            checkValue(removBrackets(key), in, false);
        } else {
            checkValue(key, in, true);
        }
    }

    /**
     * 去除中括号
     * 
     * @param key
     *            待校验的Key
     * @return 去除中括号的字符串
     */
    private static String removBrackets(String key) {
        return key.substring(1, key.length() - 1);
    }

    /**
     * 是否是必须的字段
     * 
     * @param key
     *            待校验的Key
     * @return 必须字段返回<tt>true</tt>, 否则返回<tt>false</tt>
     */
    private static boolean isOptional(String key) {
        return key.startsWith("[") && key.endsWith("]");
    }

    /**
     * 真正的校验逻辑，通过配置的正则表达式校验字段是否符合规范
     * 
     * @param key
     *            待校验字段和需要放入到DC的目标字段
     * @param must
     *            字段是否是必须的
     * @throws Exception
     */
    protected static void checkValue(String key, JSONObject in,
                                     boolean must) throws BusinessException {

        String value = in.getString(key);
        if (StringUtil.isEmpty(value)) {
            // 必须字段为空，抛出异常
            if (must) {
                String errMsg = String.format("Request Parameter %s is Empty", key);
                logger.info(errMsg);
                throw new BusinessException(errMsg);
            }
        } else {
            String regex = SpringUtil.getProperty("Regex." + key);
            if (StringUtil.isEmpty(regex)) {
                logger.info(String.format("Request Parameter %s found no regex info", key));
                return;
            }
            // 不符合正则表达式
            if (!value.matches(regex)) {
                String errMsg = String.format("Request Parameter %s[%s] cannot match the regex[%s]",
                    key, value, regex);
                logger.info(errMsg);
                throw new BusinessException(errMsg);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("2016-08-11".matches("\\d{4}-\\d{2}-\\d{2}"));
        System.out.println("11".matches("\\d{1,9}\\.{0,1}\\d{0,2}"));
    }
}
