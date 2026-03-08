package com.vnmaker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vnmaker.entity.GameNovel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameMapper extends BaseMapper<GameNovel> {
}