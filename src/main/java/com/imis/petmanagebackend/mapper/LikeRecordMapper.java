package com.imis.petmanagebackend.mapper;

import com.imis.petmanagebackend.entity.LikeRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 64360
* @description 针对表【like_record(点赞记录表)】的数据库操作Mapper
* @createDate 2026-01-21 17:30:04
* @Entity com.imis.petmanagebackend.entity.LikeRecord
*/
@Repository
@Mapper
public interface LikeRecordMapper extends BaseMapper<LikeRecord> {

}




