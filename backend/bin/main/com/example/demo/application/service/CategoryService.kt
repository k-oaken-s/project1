package com.example.demo.application.service

import com.example.demo.domain.model.Category
import com.example.demo.domain.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun getAllCategory(): List<Category> = categoryRepository.findAll()

    fun getCategoryById(id: String): Category? = categoryRepository.findById(id).orElse(null)

    fun addCategory(category: Category): Category = categoryRepository.save(category)

    fun deleteCategory(id: String) = categoryRepository.deleteById(id)
}
