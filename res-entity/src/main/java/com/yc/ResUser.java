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
 * 用户类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("resuser")
public class ResUser implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer userid; //用户编号
    private String username; //用户名
    private String pwd; //用户密码
    private String email; //用户邮箱
}
