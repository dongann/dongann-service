package com.dongann.app.dao.mall.write.cuser;

import com.dongann.app.entity.cuser.Cusers;
import org.springframework.stereotype.Repository;

@Repository
public interface CusersWriteDAO {

    /**
     * 保存用户
     * @param cusers
     * @return
     */
    public Integer insertCusers(Cusers cusers);

    /**
     * 修改用户
     * @param cusers
     * @return
     */
    public Integer updateCusers(Cusers cusers);

    /**
     * 更新用户信息，只允许更新部分字段
     * @param cusers
     * @return
     */
    public Integer updateSomeFields(Cusers cusers);
}