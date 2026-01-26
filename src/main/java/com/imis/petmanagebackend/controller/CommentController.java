package com.imis.petmanagebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.petmanagebackend.common.Result;
import com.imis.petmanagebackend.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/comment")
@Slf4j
public class CommentController {

  @Autowired
  private CommentService commentService;

  /**
   * 分页查询评论
   */
  @GetMapping("/page")
  public Result<?> getCommentPage(
      @RequestParam(required = false) Long postId,
      @RequestParam(required = false) Long userId,
      @RequestParam(required = false) Integer status,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer pageSize) {
    Page<Map<String, Object>> pageInfo = commentService.getCommentPage(postId, userId, status, page, pageSize);
    return Result.success(pageInfo);
  }

  /**
   * 更新评论状态（屏蔽/恢复）
   */
  @PutMapping("/status")
  public Result<?> updateStatus(@RequestParam Long id, @RequestParam Integer status) {
    if (status != 0 && status != 1) {
      return Result.fail("状态值无效，只能是0(屏蔽)或1(正常)");
    }
    boolean flag = commentService.updateStatus(id, status);
    return flag ? Result.success("状态更新成功") : Result.fail("状态更新失败");
  }

  /**
   * 删除评论
   */
  @DeleteMapping("/delete/{id}")
  public Result<?> deleteComment(@PathVariable Long id) {
    boolean flag = commentService.removeById(id);
    return flag ? Result.success("删除成功") : Result.fail("删除失败");
  }
}
