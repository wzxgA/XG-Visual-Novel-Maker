package com.vnmaker.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class GameNovel {

    @JsonIgnore
    private Long id; //游戏id
    private String name; //游戏名称
    private String author; //作者
    private String introduction; //游戏介绍
    private String cover; //游戏封面
    private String bgmUrl; //游戏背景音乐url
    private String coverUrl; //游戏封面url
    @JsonIgnore
    private Date createTime; //创建时间
}