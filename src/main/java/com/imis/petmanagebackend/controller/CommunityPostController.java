package com.imis.petmanagebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.petmanagebackend.common.Result;
import com.imis.petmanagebackend.entity.CommunityPost;
import com.imis.petmanagebackend.service.CommunityPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/post")
@Slf4j
public class CommunityPostController {

  @Autowired
  private CommunityPostService communityPostService;

  /**
   * 分页查询帖子
   */
  @GetMapping("/page")
  public Result<?> getPostPage(
      @RequestParam(required = false) Long userId,
      @RequestParam(required = false) String title,
      @RequestParam(required = false) Integer status,
      @RequestParam(required = false) Integer type,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer pageSize) {
    Page<Map<String, Object>> pageInfo = communityPostService.getPostPage(userId, title, status, type, page, pageSize);
    return Result.success(pageInfo);
  }

  /**
   * 发布公告帖（管理员发帖）
   */
  @PostMapping
  public Result<?> addPost(@RequestBody CommunityPost post) {
    // 管理员发帖，type 默认为 2（公告）
    if (post.getType() == null) {
      post.setType(2);
    }
    // 初始化点赞数
    if (post.getLikeCount() == null) {
      post.setLikeCount(0);
    }
    // 默认状态正常
    if (post.getStatus() == null) {
      post.setStatus(1);
    }
    boolean flag = communityPostService.save(post);
    return flag ? Result.success("发布成功") : Result.fail("发布失败");
  }

  /**
   * 获取帖子详情
   */
  @GetMapping("/{id}")
  public Result<?> getPostById(@PathVariable Long id) {
    CommunityPost post = communityPostService.getById(id);
    if (post == null) {
      return Result.fail("帖子不存在");
    }
    return Result.success(post);
  }

  /**
   * 更新帖子状态（屏蔽/恢复）
   */
  @PutMapping("/status")
  public Result<?> updateStatus(@RequestParam Long id, @RequestParam Integer status) {
    if (status != 0 && status != 1) {
      return Result.fail("状态值无效，只能是0(屏蔽)或1(正常)");
    }
    boolean flag = communityPostService.updateStatus(id, status);
    return flag ? Result.success("状态更新成功") : Result.fail("状态更新失败");
  }

  /**
   * 修改点赞数
   */
  @PutMapping("/likeCount")
  public Result<?> updateLikeCount(@RequestParam Long id, @RequestParam Integer likeCount) {
    if (likeCount < 0) {
      return Result.fail("点赞数不能为负数");
    }
    boolean flag = communityPostService.updateLikeCount(id, likeCount);
    return flag ? Result.success("点赞数更新成功") : Result.fail("点赞数更新失败");
  }

  /**
   * 删除帖子
   */
  @DeleteMapping("/{id}")
  public Result<?> deletePost(@PathVariable Long id) {
    boolean flag = communityPostService.removeById(id);
    return flag ? Result.success("删除成功") : Result.fail("删除失败");
  }
}
