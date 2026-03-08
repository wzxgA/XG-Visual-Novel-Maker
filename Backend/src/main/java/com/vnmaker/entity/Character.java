package com.vnmaker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Character {

    @JsonIgnore
    private Long id;//角色id

    private String name;//角色名称

    private Long rate;//角色进度

    @JsonIgnore
    private List<String> imageUrl;//角色形象url
}
