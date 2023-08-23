package com.yc;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppMain.class})

@ActiveProfiles("docker") //表示启用 application-init配置文件
public class TestLocalDbinit {

    @Autowired
    private DataSource ds;

    @Test
    public void testDataSource(){
        Assert.notNull(ds);
        System.out.println(ds);
    }

}
