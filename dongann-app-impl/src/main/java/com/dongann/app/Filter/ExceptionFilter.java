package com.dongann.app.Filter;

import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import com.dongann.app.common.ErrCodes;
import com.dongann.app.common.ServiceResult;
import com.dongann.app.exception.BusinessException;
import com.dongann.app.utils.SerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            //设置线程名
            String newThreadName = "S" + SerialUtil.nextSeq(".service", 8);
            Thread.currentThread().setName(newThreadName);

            logger.info("请求方法: "+invocation.getMethodName()+ ", 请求参数: "+ JSON.toJSONString(invocation.getArguments()));
            Result result = invoker.invoke(invocation);
            Object res = result.getValue();
            if(res!=null) {
                logger.info("接口响应:" + res);
            }
            if (!result.hasException()) {
                return result;
            }

            Throwable e = result.getException();
            if (e instanceof IllegalArgumentException) {
                logger.error("参数异常", e);
                return getRpcResult(ServiceResult.getError(ErrCodes.PARAM_ERROR));
            } else if (e instanceof BusinessException) {
                logger.error("处理异常", e);
                return getRpcResult(ServiceResult.getError(ErrCodes.BUSINESS_ERROR, e.getMessage()));
            } else if (e instanceof Exception) {
                logger.error("系统异常", e);
                return getRpcResult(ServiceResult.getError(ErrCodes.SYSTEM_ERROR));
            }
        } catch (Exception e) {
            logger.error("系统异常", e);
            return getRpcResult(ServiceResult.getError(ErrCodes.SYSTEM_ERROR));
        }
        return getRpcResult(ServiceResult.getError(ErrCodes.SYSTEM_ERROR));
    }

    private RpcResult getRpcResult(ServiceResult serviceResult) {
        RpcResult rlt = new RpcResult();
        rlt.setValue(serviceResult);
        logger.info("接口响应:" + serviceResult);
        return rlt;
    }

}