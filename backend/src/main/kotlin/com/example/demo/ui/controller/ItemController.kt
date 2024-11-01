package com.example.demo.ui.controller

import com.example.demo.application.service.ItemService
import com.example.demo.domain.model.Item
import com.example.demo.ui.controller.dto.AddItemRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categories/{categoryId}/items")
class ItemController(private val itemService: ItemService) {

    @GetMapping
    fun getItemsByCategoryId(@PathVariable categoryId: String): List<Item> {
        return itemService.getItemsByCategoryId(categoryId)
    }

    @PostMapping
    fun addItem(@PathVariable categoryId: String, @RequestBody addItemRequest: AddItemRequest): Item {
        return itemService.addItemToCategory(categoryId, addItemRequest.name)
    }
}
