package com.yc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 商品类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("resfood")
public class ResFood implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer fid;  //食品编号
    private String fname; //食品名称
    private Double normprice; //原价
    private Double realprice; //现价
    private String detail; //详情
    private String fphoto; //图片
    //增加一个属性 点赞数 这个属性的值是redis提供的 不是数据库
    @TableField(select = false) //mybatisplus 在做操作时忽略
    private Long praise;
}

//@TableId 表明为主键
//不能用基本类型 而用包装类
//此实体类要implements seridlizable 因此对象有序列要求
