package com.dongann.app.service.cuser;

import com.dongann.app.common.ServiceResult;
import com.dongann.app.entity.cuser.Cusers;

public interface CusersService {

    /**
     * 通过token获取用户信息
     * @param token
     * @return
     */
    public ServiceResult getCuserByToken(String token);

    /**
     * 分页获取用户列表
     * @param offset
     * @param length
     * @return
     */
    public ServiceResult getCusersPageList(Integer offset,Integer length);

}
