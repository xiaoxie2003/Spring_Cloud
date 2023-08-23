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
 * 订单详情类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("resorderitem")
public class ResOrderItem implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer roiid; //订单详情编号
    private Integer roid; //订单编号
    private Integer fid; //商品编号
    private Double dealprice; //现价
    private Integer num; //数量
}
