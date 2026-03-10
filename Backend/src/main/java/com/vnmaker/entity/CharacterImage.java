package com.vnmaker.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("character_image")
public class CharacterImage {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long characterId;

    private String imageUrl;

    private LocalDateTime createTime;
}