package com.imis.petmanagebackend.service;

import com.imis.petmanagebackend.entity.CommunityPost;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author 64360
 * @description 针对表【community_post(社区帖子表)】的数据库操作Service
 * @createDate 2026-01-21 17:29:58
 */
public interface CommunityPostService extends IService<CommunityPost> {

  /**
   * 分页查询帖子（带用户名）
   */
  Page<Map<String, Object>> getPostPage(Long userId, String title, Integer status, Integer type, Integer page,
      Integer pageSize);

  /**
   * 更新帖子状态（屏蔽/恢复）
   */
  boolean updateStatus(Long id, Integer status);

  /**
   * 修改点赞数
   */
  boolean updateLikeCount(Long id, Integer likeCount);
}
