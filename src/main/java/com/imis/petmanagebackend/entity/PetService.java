package com.imis.petmanagebackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName pet_service
 */
@TableName(value = "pet_service")
@Data
public class PetService {
    private Long id;

    private String name;

    private Integer type;

    private BigDecimal price;

    private Integer duration;

    private String description;

    private String imageUrl;

    private Integer sort;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}