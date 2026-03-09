package com.vnmaker.controller;

import com.vnmaker.entity.GameCharacter;
import com.vnmaker.service.CharacterService;
import com.vnmaker.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/character")
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping
    public ResponseUtil<?> createCharacter(@RequestBody GameCharacter character) {
        log.info("Creating character: {}", character);
        boolean result = characterService.createCharacter(character);
        return ResponseUtil.success(result);
    }

    @PutMapping
    public ResponseUtil<?> updateCharacter(@RequestBody GameCharacter character) {
        boolean result = characterService.updateCharacter(character);
        return ResponseUtil.success(result);
    }

    @DeleteMapping("/{id}")
    public ResponseUtil<?> deleteCharacter(@PathVariable("id") Long id) {
        boolean result = characterService.deleteCharacter(id);
        return ResponseUtil.success(result);
    }

    @GetMapping("/{id}")
    public ResponseUtil<GameCharacter> getCharacter(@PathVariable("id") Long id) {
        GameCharacter character = characterService.getCharacter(id);
        return ResponseUtil.success(character);
    }

    @GetMapping("/game/{gameId}")
    public ResponseUtil<List<GameCharacter>> getCharactersByGameId(@PathVariable("gameId") Long gameId) {
        List<GameCharacter> characters = characterService.getCharactersByGameId(gameId);
        return ResponseUtil.success(characters);
    }
}
