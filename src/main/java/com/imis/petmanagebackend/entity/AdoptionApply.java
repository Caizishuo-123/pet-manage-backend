package com.imis.petmanagebackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName adoption_apply
 */
@TableName(value ="adoption_apply")
@Data
public class AdoptionApply {
    private Long id;

    private Long userId;

    private Long petId;

    private String applyReason;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}