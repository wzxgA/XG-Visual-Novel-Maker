package com.vnmaker.service;

import com.vnmaker.entity.PlotNode;
import com.vnmaker.mapper.PlotMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlotService {

    private final PlotMapper plotMapper;

    public boolean createPlot(PlotNode<?> plotNode) {
        return plotMapper.insert(plotNode) > 0;
    }

    public boolean updatePlot(PlotNode<?> plotNode) {
        return plotMapper.updateById(plotNode) > 0;
    }

    public boolean deletePlot(Long id) {
        return plotMapper.deleteById(id) > 0;
    }

    public PlotNode<?> getPlot(Long id) {
        return plotMapper.selectById(id);
    }

    public List<PlotNode<?>> getPlotsByGameId(Long gameId) {
        return plotMapper.selectByGameId(gameId);
    }
}