package com.dongann.app.model.cuser;

import com.dongann.app.common.AppPaginationBean;
import com.dongann.app.common.ErrCodes;
import com.dongann.app.exception.BusinessException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dongann.app.dao.mall.read.cuser.CusersReadDAO;
import com.dongann.app.entity.cuser.Cusers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CuserModel {

    @Autowired
    private CusersReadDAO cusersReadDAO;

    /**
     * 按cuserId获取用户信息
     * @param cuserId
     * @return
     */
    public Cusers getCuserInfo(Integer cuserId){
        return cusersReadDAO.getCuserInfo(cuserId);
    }

    /**
     * 按token获取用户信息
     * @param token
     * @return
     */
    public Cusers getCuserByToken(@Param(value = "token") String token) {
        return cusersReadDAO.getByToken(token);
    }

    /**
     * 分页获取用户列表
     * @param offset
     * @param length
     * @return
     */
    public Map<String,Object> getCusersPageList(Integer offset, Integer length){
        Map<String,Object> resultMap = new HashMap<>();
        if(offset == null){
            throw new BusinessException(ErrCodes.PARAM_ERROR,"分页偏移量不能为空");
        }
        if(length == null || length.intValue() == 0){
            throw new BusinessException(ErrCodes.PARAM_ERROR,"分页长度不能为空或者0");
        }
        List<Cusers> cusersList = cusersReadDAO.getCusersPageList(offset,length+1);
        AppPaginationBean appPaginationBean = new AppPaginationBean(cusersList, length);
        resultMap.put("cuserList", appPaginationBean.getProcessedList());
        resultMap.put("hasMore", appPaginationBean.isHasMore());
        return resultMap;
    }

}
