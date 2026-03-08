package com.vnmaker.controller;

import com.vnmaker.entity.PlotNode;
import com.vnmaker.service.PlotService;
import com.vnmaker.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plot")
public class PlotController {

    private final PlotService plotService;

    @PostMapping
    public ResponseUtil<?> createPlot(@RequestBody PlotNode plotNode) {
        boolean result = plotService.createPlot(plotNode);
        return ResponseUtil.success(result);
    }

    @PutMapping
    public ResponseUtil<?> updatePlot(@RequestBody PlotNode plotNode) {
        boolean result = plotService.updatePlot(plotNode);
        return ResponseUtil.success(result);
    }

    @DeleteMapping("/{id}")
    public ResponseUtil<?> deletePlot(@PathVariable Long id) {
        boolean result = plotService.deletePlot(id);
        return ResponseUtil.success(result);
    }

    @GetMapping("/{id}")
    public ResponseUtil<PlotNode> getPlot(@PathVariable Long id) {
        PlotNode plotNode = plotService.getPlot(id);
        return ResponseUtil.success(plotNode);
    }

    @GetMapping("/game/{gameId}")
    public ResponseUtil<List<PlotNode>> getPlotsByGameId(@PathVariable Long gameId) {
        List<PlotNode> plots = plotService.getPlotsByGameId(gameId);
        return ResponseUtil.success(plots);
    }
}