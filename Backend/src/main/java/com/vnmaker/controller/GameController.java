package com.vnmaker.controller;

import com.vnmaker.entity.GameNovel;
import com.vnmaker.service.GameService;
import com.vnmaker.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game")
public class GameController {


    private final GameService gameService;

    @PostMapping
    public ResponseUtil<?> createGame(@RequestBody GameNovel gameNovel) {
        boolean result = gameService.createGame(gameNovel);
        return ResponseUtil.success(result);
    }

    @PutMapping
    public ResponseUtil<?> updateGame(@RequestBody GameNovel gameNovel) {
        boolean result = gameService.updateGame(gameNovel);
        return ResponseUtil.success(result);
    }

    @DeleteMapping("/{id}")
    public ResponseUtil<?> deleteGame(@PathVariable("id") Long id) {
        boolean result = gameService.deleteGame(id);
        return ResponseUtil.success(result);
    }

    @GetMapping("/{id}")
    public ResponseUtil<GameNovel> getGame(@PathVariable("id") Long id) {
        GameNovel gameNovel = gameService.getGame(id);
        return ResponseUtil.success(gameNovel);
    }

    @GetMapping
    public ResponseUtil<List<GameNovel>> getGames() {
        List<GameNovel> games = gameService.getGames();
        return ResponseUtil.success(games);
    }
}
