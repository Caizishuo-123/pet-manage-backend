package com.imis.petmanagebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.petmanagebackend.entity.Comment;
import com.imis.petmanagebackend.entity.CommunityPost;
import com.imis.petmanagebackend.entity.User;
import com.imis.petmanagebackend.service.CommentService;
import com.imis.petmanagebackend.service.CommunityPostService;
import com.imis.petmanagebackend.service.UserService;
import com.imis.petmanagebackend.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 64360
 * @description 针对表【comment(评论表)】的数据库操作Service实现
 * @createDate 2026-01-21 17:30:01
 */
@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommunityPostService communityPostService;

    @Override
    public Page<Map<String, Object>> getCommentPage(Long postId, Long userId, Integer status, Integer page,
            Integer pageSize) {
        Page<Comment> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(postId != null, Comment::getPostId, postId)
                .eq(userId != null, Comment::getUserId, userId)
                .eq(status != null, Comment::getStatus, status)
                .orderByDesc(Comment::getCreateTime);

        Page<Comment> commentPage = this.page(pageInfo, queryWrapper);

        Page<Map<String, Object>> resultPage = new Page<>(page, pageSize);
        resultPage.setTotal(commentPage.getTotal());

        List<Map<String, Object>> records = commentPage.getRecords().stream().map(comment -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", comment.getId());
            map.put("postId", comment.getPostId());
            map.put("userId", comment.getUserId());
            map.put("content", comment.getContent());
            map.put("status", comment.getStatus());
            map.put("createTime", comment.getCreateTime());

            // 获取用户名
            User user = userService.getById(comment.getUserId());
            map.put("username", user != null ? user.getUsername() : "未知用户");

            // 获取帖子标题
            CommunityPost post = communityPostService.getById(comment.getPostId());
            map.put("postTitle", post != null ? post.getTitle() : "未知帖子");

            return map;
        }).collect(Collectors.toList());

        resultPage.setRecords(records);
        return resultPage;
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        LambdaUpdateWrapper<Comment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Comment::getId, id)
                .set(Comment::getStatus, status);
        return this.update(updateWrapper);
    }
}
