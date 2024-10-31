package com.example.demo.ui.controller

import com.example.demo.application.service.CategoryService
import com.example.demo.domain.model.Category
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/games")
class GameController(private val categoryService: CategoryService) {

    @GetMapping
    fun getAllGames(): List<Category> = categoryService.getAllGames()

    @GetMapping("/{gameId}")
    fun getGameById(@PathVariable gameId: String): ResponseEntity<Category> {
        val game = categoryService.getCategoryById(gameId)
        return if (game != null) ResponseEntity.ok(game) else ResponseEntity.notFound().build()
    }
}
