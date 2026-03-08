package com.vnmaker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vnmaker.entity.PlotNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlotMapper extends BaseMapper<PlotNode> {
    List<PlotNode> selectByGameId(@Param("gameId") Long gameId);
}