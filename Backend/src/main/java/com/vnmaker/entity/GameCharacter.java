package com.vnmaker.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.util.List;

@Data
@TableName("game_character")
public class GameCharacter {

    @TableId(type = IdType.AUTO)
    private Long id;//角色id

    private Long gameId;//游戏id

    private String name;//角色名称

    private Long rate;//角色进度

    private String imageUrl;//角色形象url

    @TableField(exist = false)
    private List<String> imageUrls;//角色形象url列表
}
