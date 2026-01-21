package com.imis.petmanagebackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName orders
 */
@TableName(value ="orders")
@Data
public class Orders {
    private Long id;

    private Long userId;

    private Long appointmentId;

    private BigDecimal totalPrice;

    private Integer payStatus;

    private Date createTime;

    private Date updateTime;
}