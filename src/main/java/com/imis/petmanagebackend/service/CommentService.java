package com.imis.petmanagebackend.service;

import com.imis.petmanagebackend.entity.Comment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author 64360
 * @description 针对表【comment(评论表)】的数据库操作Service
 * @createDate 2026-01-21 17:30:01
 */
public interface CommentService extends IService<Comment> {

  /**
   * 分页查询评论（带用户名、帖子标题）
   */
  Page<Map<String, Object>> getCommentPage(Long postId, Long userId, Integer status, Integer page, Integer pageSize);

  /**
   * 更新评论状态（屏蔽/恢复）
   */
  boolean updateStatus(Long id, Integer status);
}
