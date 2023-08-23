package com.yc.resfood.biz;

import com.yc.ResOrder;
import com.yc.ResUser;
import com.yc.resfood.model.CartItem;

import java.util.Set;

/**
 * 保存订单 事务操作
 */
public interface ResorderBiz{

    /**
     * 下单操作
     * @param resOrder 订单相关信息 address tel pa status
     * @param cartItems 订单项信息 CartItem<roid fid dealprice num>
     * @param resUser 用户信息 userid
     * @return
     */
    public int order(ResOrder resOrder, Set<CartItem> cartItems, ResUser resUser);
}
