package com.example.demo.ui.controller

import com.example.demo.application.service.CharacterService
import com.example.demo.application.service.GameService
import com.example.demo.domain.model.Character
import com.example.demo.domain.model.Game
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController(
        private val gameService: GameService,
        private val characterService: CharacterService
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Map<String, String>> {
        if (loginRequest.username == "admin" && loginRequest.password == "password") {
            val token = JwtUtil.generateToken(loginRequest.username)
            return ResponseEntity.ok(mapOf("token" to token))
        }
        return ResponseEntity.status(401).build()
    }

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
    fun deleteCharacter(@PathVariable characterId: String) =
            characterService.deleteCharacter(characterId)
}
