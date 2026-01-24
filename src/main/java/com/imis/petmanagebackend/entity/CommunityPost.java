package com.imis.petmanagebackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName community_post
 */
@TableName(value = "community_post")
@Data
public class CommunityPost {
    private Long id;

    private Long userId;

    private Integer type;

    private String title;

    private String content;

    private String imageUrl;

    private Integer likeCount;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}