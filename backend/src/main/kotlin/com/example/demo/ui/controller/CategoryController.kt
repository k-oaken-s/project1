package com.example.demo.ui.controller

import com.example.demo.application.service.CategoryService
import com.example.demo.domain.model.Category
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping fun getAllGames(): List<Category> = categoryService.getAllCategory()

    @GetMapping("/{categoryId}")
    fun getGameById(@PathVariable categoryId: String): ResponseEntity<Category> {
        val category = categoryService.getCategoryById(categoryId)
        return if (category != null) ResponseEntity.ok(category) else ResponseEntity.notFound().build()
    }

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    fun addCategory(@RequestBody category: Category): ResponseEntity<Category> {
        val createdCategory = categoryService.addCategory(category)
        return ResponseEntity.ok(createdCategory)
    }
}
