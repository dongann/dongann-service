package com.dongann.app.common;

import com.alibaba.fastjson.JSON;
import com.dongann.app.exception.BusinessException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: ServiceResult
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/6/1 下午8:26
 * @Version: v1.0
 * @description:
 */
public class ServiceResult extends HashMap implements Serializable {

    private Boolean isSuccess;

    public Boolean getIsSuccess() {
        if(this.get("retCode") == null){
            return false;
        }
        return ErrCodes.SUCCESS.getCode().equals((String)(this.get("retCode")));
    }

    public ServiceResult(String retCode, String retMsg) {
        this.put("retCode",retCode);
        this.put("retMsg",retMsg);
    }

    public ServiceResult(ErrCodes errCodes) {
        this.put("retCode",errCodes.getCode());
        this.put("retMsg",errCodes.getMsg());
    }

    public ServiceResult(ErrCodes errCodes, String retMsg) {
        this.put("retCode",errCodes.getCode());
        this.put("retMsg",retMsg);
    }


    public ServiceResult() {
        this.put("retCode",ErrCodes.SUCCESS.getCode());
        this.put("retMsg",SystemConstants.DEFAULT_SUCCESS_RETURNMSG);
    }

    public String getRetCode() {
        if(this.get("retCode") !=null){
            return (String)this.get("retCode");
        }
        return "";
    }

    public void setRetCode(String retCode) {
        this.put("retCode",retCode);
    }

    public String getRetMsg() {
        if(this.get("retMsg") !=null){
            return (String)this.get("retMsg");
        }
        return "";
    }

    public void setRetMsg(String retMsg) {
        this.put("retMsg",retMsg);
    }


    /**
     * 获取正确结果模板
     *
     * @param message 请求返回信息
     * @param resultMap     请求结果
     * @return ServiceResult
     */
    public static ServiceResult getSuccess(String message, Map<String,Object> resultMap) {
        ServiceResult result = new ServiceResult();
        result.put("retMsg",message);
        if(resultMap != null && !resultMap.isEmpty()){
            result.putAll(resultMap);
        }
        return result;
    }

    /**
     * 获取正确结果模板
     *
     * @param resultMap 请求结果
     * @return ServiceResult
     */
    public static ServiceResult getSuccess(Map<String,Object> resultMap) {
        ServiceResult result = new ServiceResult();
        if(resultMap !=null && !resultMap.isEmpty()){
            result.putAll(resultMap);
        }
        return result;
    }

    /**
     * 获取错误结果模板
     *
     * @param retMsg 请求返回信息
     * @param resultMap     请求结果
     * @return ServiceResult
     */
    public static ServiceResult getError(ErrCodes errorCode, String retMsg, Map<String,Object> resultMap) {
        ServiceResult result = new ServiceResult();
        result.put("retCode",errorCode.getCode());
        result.put("retMsg",retMsg);
        if(resultMap !=null && !resultMap.isEmpty()){
            result.putAll(resultMap);
        }
        return result;
    }

    /**
     * 获取错误结果模板
     *
     * @param retCode
     * @param retMsg
     * @return
     */
    public static ServiceResult getError(ErrCodes retCode, String retMsg) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.put("retCode",retCode.getCode());
        serviceResult.put("retMsg",retMsg);
        return serviceResult;
    }

    /**
     * 获取错误结果模板
     *
     * @param retCode
     * @param retMsg
     * @param resultMap
     * @return
     */
    public static ServiceResult getError(String retCode, String retMsg, Map<String,Object> resultMap) {
        ServiceResult result = new ServiceResult();
        result.put("retCode",retCode);
        result.put("retMsg",retMsg);
        if(resultMap !=null && !resultMap.isEmpty()){
            result.putAll(resultMap);
        }
        return result;
    }

    /**
     * 获取错误结果模板
     *
     * @param retCode
     * @param retMsg
     * @return
     */
    public static ServiceResult getError(String retCode, String retMsg) {
        ServiceResult result = new ServiceResult();
        result.put("retCode",retCode);
        result.put("retMsg",retMsg);
        return result;
    }

    /**
     * 获取正确结果模板
     *
     * @return ServiceResult
     */
    public static ServiceResult getSuccess() {
        return getSuccess(SystemConstants.DEFAULT_SUCCESS_RETURNMSG, null);
    }


    /**
     * 获取错误结果模板
     *
     * @return ServiceResult
     */
    public static ServiceResult getError(ErrCodes retCode) {
        return getError(retCode, retCode.getMsg());
    }

    public static ServiceResult getError(BusinessException be) {
        return getError(be.getRetCode(), be.getMessage());
    }
    /**
     * response json string
     *
     * @param o
     * @param response
     */
    public static void writerJson(Object o, HttpServletResponse response) {
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(o));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> ServiceResult getSuccess(String key,T t){
        ServiceResult result = getSuccess();
        result.put(key,t);
        return result;
    }
    public static <T> ServiceResult getError(String key,T t,ErrCodes errCode){
        ServiceResult result = getError(errCode);
        result.put(key,t);
        return result;
    }

}
