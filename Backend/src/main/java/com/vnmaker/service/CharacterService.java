package com.vnmaker.service;

import com.vnmaker.entity.Character;
import com.vnmaker.mapper.CharacterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CharacterService {

    private final CharacterMapper characterMapper;

    public boolean createCharacter(Character character) {
        return characterMapper.insert(character) > 0;
    }

    public boolean updateCharacter(Character character) {
        return characterMapper.updateById(character) > 0;
    }

    public boolean deleteCharacter(Long id) {
        return characterMapper.deleteById(id) > 0;
    }

    public Character getCharacter(Long id) {
        return characterMapper.selectById(id);
    }

    public List<Character> getCharactersByGameId(Long gameId) {
        return characterMapper.selectByGameId(gameId);
    }
}