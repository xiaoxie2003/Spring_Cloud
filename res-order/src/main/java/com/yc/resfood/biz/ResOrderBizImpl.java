package com.yc.resfood.biz;

import com.yc.ResOrder;
import com.yc.ResOrderItem;
import com.yc.ResUser;
import com.yc.resfood.dao.ResorderDao;
import com.yc.resfood.dao.ResorderItemDao;
import com.yc.resfood.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

//事务：REQUIRED 必须事务环境下运行 比较：SUPPORTED（有没有事务都可以）
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,
            timeout = 2000,readOnly = false,rollbackFor = RuntimeException.class)
@Slf4j
public class ResOrderBizImpl implements ResorderBiz{

    @Autowired
    private ResorderDao resorderDao;

    @Autowired
    private ResorderItemDao resorderItemDao;

    @Override
    public int order(ResOrder resOrder, Set<CartItem> cartItems, ResUser resUser) {
        resOrder.setUserid(resUser.getUserid());
        this.resorderDao.insert(resOrder); //插入成功后会有roid存在在这个对象resorder中
        //一个 resorder 下可能有多上 resorderItem对象
        for(CartItem ci:cartItems){

            ResOrderItem roi = new ResOrderItem();

            roi.setRoid(resOrder.getRoid());
            roi.setFid(ci.getFood().getFid());
            roi.setDealprice(ci.getFood().getRealprice());
            roi.setNum(ci.getNum());

            this.resorderItemDao.insert(roi);
        }

        return 1;
    }
}
