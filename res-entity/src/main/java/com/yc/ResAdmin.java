package com.yc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 管理员类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("resadmin")
public class ResAdmin {
    @TableId(type = IdType.AUTO)
    private Integer raid; //管理员编号
    private String raname; //姓名
    private String rapwd; //密码
}
