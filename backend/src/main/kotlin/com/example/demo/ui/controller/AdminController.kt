package com.example.demo.ui.controller

import com.example.demo.application.service.ItemService
import com.example.demo.application.service.CategoryService
import com.example.demo.common.security.JwtUtil
import com.example.demo.domain.model.Category
import com.example.demo.domain.model.Item
import com.example.demo.ui.controller.dto.LoginRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class AdminController(
    private val categoryService: CategoryService,
    private val itemService: ItemService
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
    fun createGame(@RequestBody category: Category): Category = categoryService.saveGame(category)

    @DeleteMapping("/games/{gameId}")
    fun deleteGame(@PathVariable gameId: String) = categoryService.deleteGame(gameId)

    @PostMapping("/games/{gameId}/characters")
    fun createCharacter(
            @PathVariable categoryId: String,
            @RequestBody item: Item
    ): Item = itemService.saveCharacter(categoryId, item)

    @DeleteMapping("/characters/{characterId}")
    fun deleteCharacter(@PathVariable characterId: String) =
            itemService.deleteCharacter(characterId)
}
