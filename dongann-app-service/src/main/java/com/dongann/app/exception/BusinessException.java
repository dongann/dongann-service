package com.dongann.app.exception;

import com.dongann.app.common.ErrCodes;

/**
 * @FileName: BusinessException
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/6/5 下午11:26
 * @Version: v1.0
 * @description: 业务异常的自定义封装类
 */
public class BusinessException extends BaseException {
	private static final long serialVersionUID = -2891888846711081199L;
	private String retCode;

	public BusinessException(String message) {
		super(message,new Throwable(message));
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

    public BusinessException(String retCode, String message, Throwable cause) {
        super(message, cause);
        this.retCode = retCode;
    }
    public BusinessException(String retCode, String message) {
        super(message);
        this.retCode = retCode;
    }

    public BusinessException(ErrCodes retCode) {
        super(retCode.getMsg());
        this.retCode = retCode.getCode();
    }

    public BusinessException(ErrCodes retCode, String retMsg) {
        super(retMsg);
        this.retCode = retCode.getCode();
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }
}
