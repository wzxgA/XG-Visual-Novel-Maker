package com.vnmaker.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
public class PlotNode<T> {

    @TableId(type = IdType.AUTO)
    private Long id; // 剧情节点id   
    private String scriptJson;// 剧情脚本
    @JsonIgnore
    private List<PlotNode<?>> prePlotNode;//前置剧情节点
    @JsonIgnore
    private List<PlotNode<?>> nextPlotNode;//后置剧情节点
    
    private PreConditions<T> preConditions;// 前置条件
}
