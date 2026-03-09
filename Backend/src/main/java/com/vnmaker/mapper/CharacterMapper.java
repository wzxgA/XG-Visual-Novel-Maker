package com.vnmaker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vnmaker.entity.GameCharacter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CharacterMapper extends BaseMapper<GameCharacter> {
    List<GameCharacter> selectByGameId(@Param("gameId") Long gameId);
}
