package com.vnmaker.service;

import com.vnmaker.entity.GameCharacter;
import com.vnmaker.mapper.CharacterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CharacterService {

    private final CharacterMapper characterMapper;

    public boolean createCharacter(GameCharacter character) {
        return characterMapper.insert(character) > 0;
    }

    public boolean updateCharacter(GameCharacter character) {
        return characterMapper.updateById(character) > 0;
    }

    public boolean deleteCharacter(Long id) {
        return characterMapper.deleteById(id) > 0;
    }

    public GameCharacter getCharacter(Long id) {
        return characterMapper.selectById(id);
    }

    public List<GameCharacter> getCharactersByGameId(Long gameId) {
        return characterMapper.selectByGameId(gameId);
    }
}
