package com.sherlochao.service;

import com.sherlochao.model.Shared;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Sherlock_chao on 2016/12/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring.xml",
        "classpath:spring-mvc.xml",
        "classpath:spring-hibernate.xml"
        ,"classpath:spring-redis.xml"})
public class SharedServiceTest {

    @Resource
    SharedService sharedService;
    private Shared shared;

    @Test
    public void findBySharedId(){
//        Shared shared = sharedService.findBySharedId(4);
        System.out.println("hello world");
//        System.out.println(shared.toString());
    }
}
