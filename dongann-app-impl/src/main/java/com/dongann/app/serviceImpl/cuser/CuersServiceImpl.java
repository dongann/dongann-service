package com.dongann.app.serviceImpl.cuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dongann.app.common.ServiceResult;
import com.dongann.app.model.cuser.CuserModel;
import com.dongann.app.service.cuser.CusersService;


@Service(version = "1.0.0", timeout = 3000)
public class CuersServiceImpl implements CusersService {
    private Logger log = LoggerFactory.getLogger(CuersServiceImpl.class);
    @Autowired
    private CuserModel cuserModel;

    @Override
    public ServiceResult getCuserByToken(String token) {
        return ServiceResult.getSuccess("cuser",cuserModel.getCuserByToken(token));
    }

    /**
     * 分页获取用户列表
     * @param offset
     * @param length
     * @return
     */
    @Override
    public ServiceResult getCusersPageList(Integer offset, Integer length) {
        return ServiceResult.getSuccess(cuserModel.getCusersPageList(offset,length));
    }

}
