package com.example.demo.application.service

import com.example.demo.domain.model.Item
import com.example.demo.domain.repository.ItemRepository
import org.springframework.stereotype.Service

@Service
class ItemService(
        private val characterRepository: ItemRepository,
        private val categoryService: CategoryService
) {

    fun getCharactersByGameId(gameId: String): List<Item> =
            characterRepository.findByCategoryId(gameId)

    fun saveCharacter(categoryId: String, item: Item): Item {
        val category =
                categoryService.getCategoryById(categoryId) ?: throw Exception("Category not found")
        val newCharacter = item.copy(category = category)
        return characterRepository.save(newCharacter)
    }

    fun deleteCharacter(id: String) = characterRepository.deleteById(id)
}
