package com.example.demo.ui.controller

import com.example.demo.application.service.GameService
import com.example.demo.domain.model.Game
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/games")
class GameController(private val gameService: GameService) {

    @GetMapping
    fun getAllGames(): List<Game> = gameService.getAllGames()

    @GetMapping("/{gameId}")
    fun getGameById(@PathVariable gameId: String): ResponseEntity<Game> {
        val game = gameService.getGameById(gameId)
        return if (game != null) ResponseEntity.ok(game) else ResponseEntity.notFound().build()
    }
}
