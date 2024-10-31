package com.example.demo.ui.controller

import com.example.demo.application.service.ItemService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.example.demo.domain.model.Item

@RestController
@RequestMapping("/api/games/{gameId}/characters")
class CharacterController(private val itemService: ItemService) {

    @GetMapping
    fun getCharactersByGameId(@PathVariable gameId: String): List<Item> {
        return itemService.getCharactersByGameId(gameId)
    }
}
