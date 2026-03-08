package com.vnmaker.controller;

import com.vnmaker.entity.Character;
import com.vnmaker.service.CharacterService;
import com.vnmaker.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/character")
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping
    public ResponseUtil<?> createCharacter(@RequestBody Character character) {
        boolean result = characterService.createCharacter(character);
        return ResponseUtil.success(result);
    }

    @PutMapping
    public ResponseUtil<?> updateCharacter(@RequestBody Character character) {
        boolean result = characterService.updateCharacter(character);
        return ResponseUtil.success(result);
    }

    @DeleteMapping("/{id}")
    public ResponseUtil<?> deleteCharacter(@PathVariable Long id) {
        boolean result = characterService.deleteCharacter(id);
        return ResponseUtil.success(result);
    }

    @GetMapping("/{id}")
    public ResponseUtil<Character> getCharacter(@PathVariable Long id) {
        Character character = characterService.getCharacter(id);
        return ResponseUtil.success(character);
    }

    @GetMapping("/game/{gameId}")
    public ResponseUtil<List<Character>> getCharactersByGameId(@PathVariable Long gameId) {
        List<Character> characters = characterService.getCharactersByGameId(gameId);
        return ResponseUtil.success(characters);
    }
}