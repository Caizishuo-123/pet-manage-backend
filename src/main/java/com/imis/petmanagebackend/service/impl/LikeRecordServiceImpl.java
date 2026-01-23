package com.imis.petmanagebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.petmanagebackend.entity.LikeRecord;
import com.imis.petmanagebackend.service.LikeRecordService;
import com.imis.petmanagebackend.mapper.LikeRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author 64360
* @description 针对表【like_record(点赞记录表)】的数据库操作Service实现
* @createDate 2026-01-21 17:30:04
*/
@Service
@Slf4j
public class LikeRecordServiceImpl extends ServiceImpl<LikeRecordMapper, LikeRecord>
    implements LikeRecordService{

}




