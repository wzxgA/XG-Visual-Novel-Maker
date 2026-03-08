package com.vnmaker.entity;

import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
public class PlotNode {

    private Long id; // 剧情节点id   
    private String scriptJson;// 剧情脚本
    @JsonIgnore
    private List<PlotNode> prePlotNode;//前置剧情节点
    @JsonIgnore
    private List<PlotNode> nextPlotNode;//后置剧情节点
}
