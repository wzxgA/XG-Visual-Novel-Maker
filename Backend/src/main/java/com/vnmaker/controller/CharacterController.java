package com.vnmaker.controller;

import com.vnmaker.entity.CharacterImage;
import com.vnmaker.entity.GameCharacter;
import com.vnmaker.mapper.CharacterImageMapper;
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
    private final CharacterImageMapper characterImageMapper;

    @PostMapping
    public ResponseUtil<?> createCharacter(@RequestBody GameCharacter character) {
        log.info("Creating character: {}", character);
        // 如果有图片URL，设置第一个URL为主要图片URL
        if (character.getImageUrls() != null && !character.getImageUrls().isEmpty()) {
            character.setImageUrl(character.getImageUrls().get(0));
        }
        boolean result = characterService.createCharacter(character);
        if (result && character.getImageUrls() != null && !character.getImageUrls().isEmpty()) {
            for (String imageUrl : character.getImageUrls()) {
                CharacterImage characterImage = new CharacterImage();
                characterImage.setCharacterId(character.getId());
                characterImage.setImageUrl(imageUrl);
                characterImageMapper.insert(characterImage);
            }
        }
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
