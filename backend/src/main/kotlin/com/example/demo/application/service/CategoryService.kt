package com.example.demo.application.service

import com.example.demo.domain.model.Category
import com.example.demo.domain.repository.GameRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val gameRepository: GameRepository) {

    fun getAllGames(): List<Category> = gameRepository.findAll()

    fun getCategoryById(id: String): Category? = gameRepository.findById(id).orElse(null)

    fun saveGame(category: Category): Category = gameRepository.save(category)

    fun deleteGame(id: String) = gameRepository.deleteById(id)
}
