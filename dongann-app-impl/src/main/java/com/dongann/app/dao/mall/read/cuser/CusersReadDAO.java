package com.dongann.app.dao.mall.read.cuser;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.dongann.app.datasource.DataSource;
import com.dongann.app.datasource.DataSourceType;
import com.dongann.app.entity.cuser.Cusers;

@Component
@ComponentScan
public interface CusersReadDAO {
    /**
     * 获取用户信息
     * @param cuserId
     * @return
     */
    Cusers getByCuserId(Integer cuserId);

    /**
     * 获取用户信息
     * @param cuserId
     * @return
     */
    Cusers getCuserInfo(Integer cuserId);

    /**
     * 通过token获取用户信息
     * @param token
     * @return
     */
    Cusers getByToken(@Param(value = "token") String token);

    /**
     * 通过手机号获取用户信息
     * @param cuserMobile
     * @return
     */
    Cusers getByCuserMobile(String cuserMobile);

    /**
     * 通过邀请码获取用户信息
     * @param inviteCode
     * @return
     */
    Cusers getCuserByInviteCode(String inviteCode);

    /**
     * 分页获取用户列表
     * @param offset
     * @param length
     * @return
     */
    @DataSource(DataSourceType.SLAVE)
    List<Cusers> getCusersPageList(@Param(value = "offset") Integer offset,@Param(value = "length") Integer length);

}