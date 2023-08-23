package com.yc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 订单类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("resorder")
public class ResOrder implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer roid; //订单编号
    private Integer userid; //用户编号
    private String address; //地址
    private String tel; //用户电话号码
    private String ordertime; //下单时间
    private String deliverytime; //送单时间
    private String ps; //附言
    private Integer status;  //状态
}
