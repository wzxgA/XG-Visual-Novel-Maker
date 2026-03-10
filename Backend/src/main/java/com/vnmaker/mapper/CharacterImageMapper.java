package com.vnmaker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vnmaker.entity.CharacterImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CharacterImageMapper extends BaseMapper<CharacterImage> {

    List<CharacterImage> selectByCharacterId(@Param("characterId") Long characterId);
}