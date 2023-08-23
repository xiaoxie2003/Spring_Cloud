package com.yc;

import com.yc.biz.ResFoodBiz;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class TestFood {
    @Autowired
    private ResFoodBiz resFoodBiz;

    @Test
    public void TestFindAll(){
        List<ResFood> list = resFoodBiz.FindAll();
        System.out.println(list);
    }

    @Test
    public void TestFindById(){
        ResFood resFood = resFoodBiz.FindById(5);
        System.out.println(resFood);
    }

    @Test
    public void TestFindByName(){
        List<ResFood> list = resFoodBiz.FindByName("炒");
        System.out.println(list);
    }
}
