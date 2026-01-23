package com.imis.petmanagebackend.mapper;

import com.imis.petmanagebackend.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 64360
* @description 针对表【comment(评论表)】的数据库操作Mapper
* @createDate 2026-01-21 17:30:01
* @Entity com.imis.petmanagebackend.entity.Comment
*/
@Repository
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




