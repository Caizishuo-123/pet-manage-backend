package com.imis.petmanagebackend.mapper;

import com.imis.petmanagebackend.entity.CommunityPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 64360
* @description 针对表【community_post(社区帖子表)】的数据库操作Mapper
* @createDate 2026-01-21 17:29:58
* @Entity com.imis.petmanagebackend.entity.CommunityPost
*/
@Repository
@Mapper
public interface CommunityPostMapper extends BaseMapper<CommunityPost> {

}




