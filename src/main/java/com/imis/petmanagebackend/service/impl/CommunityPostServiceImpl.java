package com.imis.petmanagebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.petmanagebackend.entity.CommunityPost;
import com.imis.petmanagebackend.entity.User;
import com.imis.petmanagebackend.service.CommunityPostService;
import com.imis.petmanagebackend.service.UserService;
import com.imis.petmanagebackend.mapper.CommunityPostMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 64360
 * @description 针对表【community_post(社区帖子表)】的数据库操作Service实现
 * @createDate 2026-01-21 17:29:58
 */
@Service
@Slf4j
public class CommunityPostServiceImpl extends ServiceImpl<CommunityPostMapper, CommunityPost>
        implements CommunityPostService {

    @Autowired
    private UserService userService;

    @Override
    public Page<Map<String, Object>> getPostPage(Long userId, String title, Integer status, Integer type, Integer page,
            Integer pageSize) {
        Page<CommunityPost> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<CommunityPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(userId != null, CommunityPost::getUserId, userId)
                .like(StringUtils.hasText(title), CommunityPost::getTitle, title)
                .eq(status != null, CommunityPost::getStatus, status)
                .eq(type != null, CommunityPost::getType, type)
                .orderByDesc(CommunityPost::getCreateTime);

        Page<CommunityPost> postPage = this.page(pageInfo, queryWrapper);

        Page<Map<String, Object>> resultPage = new Page<>(page, pageSize);
        resultPage.setTotal(postPage.getTotal());

        List<Map<String, Object>> records = postPage.getRecords().stream().map(post -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", post.getId());
            map.put("userId", post.getUserId());
            map.put("type", post.getType());
            map.put("title", post.getTitle());
            map.put("content", post.getContent());
            map.put("imageUrl", post.getImageUrl());
            map.put("likeCount", post.getLikeCount());
            map.put("status", post.getStatus());
            map.put("createTime", post.getCreateTime());
            map.put("updateTime", post.getUpdateTime());

            User user = userService.getById(post.getUserId());
            map.put("username", user != null ? user.getUsername() : "未知用户");

            return map;
        }).collect(Collectors.toList());

        resultPage.setRecords(records);
        return resultPage;
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        LambdaUpdateWrapper<CommunityPost> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityPost::getId, id)
                .set(CommunityPost::getStatus, status);
        return this.update(updateWrapper);
    }

    @Override
    public boolean updateLikeCount(Long id, Integer likeCount) {
        LambdaUpdateWrapper<CommunityPost> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityPost::getId, id)
                .set(CommunityPost::getLikeCount, likeCount);
        return this.update(updateWrapper);
    }
}
