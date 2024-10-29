package com.example.demo.ui.controller

import com.example.demo.application.service.CharacterService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.example.demo.domain.model.Character

@RestController
@RequestMapping("/api/games/{gameId}/characters")
class CharacterController(private val characterService: CharacterService) {

    @GetMapping
    fun getCharactersByGameId(@PathVariable gameId: String): List<Character> {
        return characterService.getCharactersByGameId(gameId)
    }
}
