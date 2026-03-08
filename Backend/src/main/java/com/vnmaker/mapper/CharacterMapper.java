package com.vnmaker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vnmaker.entity.Character;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CharacterMapper extends BaseMapper<Character> {
    List<Character> selectByGameId(@Param("gameId") Long gameId);
}