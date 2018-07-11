package com.dongann.app.common;

/**
 * @FileName: SystemConstants
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/6/2 下午10:26
 * @Version: v1.0
 * @description:
 */
public class SystemConstants {

    /**
     * 默认值 - 执行失败时ReturnContext的ReturnMsg
     */
    public static final String DEFAULT_FAILED_RETURNMSG = "fail";
    /**
     * 默认值KEY - 执行成功时ReturnContext的ReturnMsg
     */
    public static final String DEFAULT_SUCCESS_RETURNMSG = "success";

    /**token*/
    public static final String TOKEN = "token";
    /**method*/
    public static final String METHOD = "method";
    /**param*/
    public static final String PARAM_JSON = "paramJson";

    public static final String CUSER = "cuser";

    /**
     * 如果校验用户对不上的话。
     * 比如修改收货地址时，传来
     * 的addressid不属于当前用户
     */
    public static final int ERRORUSER = -4;

}
