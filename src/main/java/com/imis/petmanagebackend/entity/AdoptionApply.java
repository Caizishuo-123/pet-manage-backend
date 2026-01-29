package com.imis.petmanagebackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName adoption_apply
 */
@TableName(value = "adoption_apply")
@Data
public class AdoptionApply {
    private Long id;

    private Long userId;

    private Long petId;

    private String applyReason;

    /**
     * 领取方式：1-上门自取 2-送宠上门
     */
    private Integer deliveryType;

    /**
     * 送达地址（送宠上门时填写）
     */
    private String address;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 状态：1-待审核 2-通过 3-拒绝 4-已完成
     */
    private Integer status;

    private Date createTime;

    private Date updateTime;
}