package com.dongann.app.cusers;

import com.dongann.app.TestBase;
import com.dongann.app.service.cuser.CusersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @FileName: CusersServiceTest
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/7/10 下午5:58
 * @Version: v1.0
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class CusersServiceTest extends TestBase {
    @Resource
    private CusersService cusersService;

    @Test
    public void testGetCuserByToken(){
        print(cusersService.getCuserByToken("5719fae0842711e89fc376e92d243085"));
    }


    @Test
    public void testGetCusersPageList(){
        print(cusersService.getCusersPageList(0,10));
    }
}
