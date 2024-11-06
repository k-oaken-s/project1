package tierMaker.application.category

import tierMaker.domain.model.Category
import tierMaker.domain.model.Item
import tierMaker.domain.repository.CategoryRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import jakarta.transaction.Transactional

@Service
class CategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    fun getAllCategories(): List<Category> = categoryRepository.findAll()

    fun getCategoryWithItems(id: String): Category =
        categoryRepository.findById(id).orElseThrow { IllegalArgumentException("Category not found") }

    fun addCategory(addCategoryDto: AddCategoryDto, imageBytes: ByteArray?): Category {
        val category = Category.create(
            name = addCategoryDto.name,
            description = addCategoryDto.description,
            image = imageBytes
        )
        return categoryRepository.save(category)
    }

    fun deleteCategory(id: String) = categoryRepository.deleteById(id)

    @Transactional
    fun addItemToCategory(categoryId: String, itemJson: String, imageBytes: ByteArray?): Item {
        val category = categoryRepository.findById(categoryId)
            .orElseThrow { IllegalArgumentException("Category not found") }
        val itemName = ObjectMapper().readTree(itemJson).get("name").asText()
        val newItem = category.addItem(name = itemName, image = imageBytes)
        categoryRepository.save(category) // cascadeにより新しいアイテムも保存される
        return newItem
    }

    @Transactional
    fun updateItemInCategory(categoryId: String, itemId: String, itemJson: String, file: MultipartFile?, keepCurrentImage: Boolean): Item {
        val category = categoryRepository.findById(categoryId)
            .orElseThrow { IllegalArgumentException("Category not found") }
        val itemName = ObjectMapper().readTree(itemJson).get("name").asText()
        val imageBytes = file?.bytes
        val updatedItem = category.updateItem(itemId = itemId, name = itemName, image = imageBytes, keepCurrentImage = keepCurrentImage)
        categoryRepository.save(category) // cascadeによりアイテムの更新が反映される
        return updatedItem
    }
}
