package com.vnmaker.service;

import com.vnmaker.entity.GameNovel;
import com.vnmaker.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GameMapper gameMapper;

    public boolean createGame(GameNovel gameNovel) {
        return gameMapper.insert(gameNovel) > 0;
    }

    public boolean updateGame(GameNovel gameNovel) {
        return gameMapper.updateById(gameNovel) > 0;
    }

    public boolean deleteGame(Long id) {
        return gameMapper.deleteById(id) > 0;
    }

    public GameNovel getGame(Long id) {
        return gameMapper.selectById(id);
    }

    public List<GameNovel> getGames() {
        return gameMapper.selectList(null);
    }
}