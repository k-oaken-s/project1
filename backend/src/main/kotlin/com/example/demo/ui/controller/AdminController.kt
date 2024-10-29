package com.example.demo.ui.controller

import com.example.demo.application.service.CharacterService
import com.example.demo.application.service.GameService
import com.example.demo.domain.model.Game
import org.springframework.web.bind.annotation.*
import com.example.demo.domain.model.Character

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val gameService: GameService,
    private val characterService: CharacterService
) {

    @PostMapping("/games")
    fun createGame(@RequestBody game: Game): Game = gameService.saveGame(game)

    @DeleteMapping("/games/{gameId}")
    fun deleteGame(@PathVariable gameId: String) = gameService.deleteGame(gameId)

    @PostMapping("/games/{gameId}/characters")
    fun createCharacter(
        @PathVariable gameId: String,
        @RequestBody character: Character
    ): Character = characterService.saveCharacter(gameId, character)

    @DeleteMapping("/characters/{characterId}")
    fun deleteCharacter(@PathVariable characterId: String) = characterService.deleteCharacter(characterId)
}
