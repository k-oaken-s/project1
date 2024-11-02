package tierMaker.application.category

import tierMaker.domain.model.Category
import tierMaker.domain.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class CategoryUseCase(private val categoryRepository: CategoryRepository) {

    fun getAllCategory(): List<Category> = categoryRepository.findAll()

    fun getCategoryById(id: String): Category? = categoryRepository.findById(id).orElse(null)

    fun addCategory(addCategoryDto: AddCategoryDto, imageBytes: ByteArray?): Category {
        val category = Category.create(
            name = addCategoryDto.name,
            description = addCategoryDto.description,
            image = imageBytes
        )

        return categoryRepository.save(category)
    }

    fun deleteCategory(id: String) = categoryRepository.deleteById(id)
}
