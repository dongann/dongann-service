package com.dongann.app.common;

/**
 * @FileName: ErrCodes
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/6/1 下午2:26
 * @Version: v1.0
 * @description:
 */
public enum ErrCodes {

    SUCCESS("0000","成功"),

    UNKNOWN_ERROR("0001","未知异常"),

    DB_ERROR("0002","数据库操作异常"),

    PARAM_ERROR("0003","参数验证错误"),

    SYSTEM_ERROR("0004","系统异常"),

    BUSINESS_ERROR("0005","业务错误"),

    INFO_ERROR("0006", "提示级错误"),

    SYSTEM_MAINTAIN_ERROR("0007","系统正在维护");

    private String _code;
    private String _msg;

    private ErrCodes(String code, String msg){
        _code = code;
        _msg = msg;
    }

    public String getCode(){
        return _code;
    }
    public String getMsg(){
        return _msg;
    }

    public static ErrCodes getByCode(String code){
        for(ErrCodes ec : ErrCodes.values()){
            if(ec.getCode().equals(code)){
                return ec;
            }
        }

        return null;
    }
}
