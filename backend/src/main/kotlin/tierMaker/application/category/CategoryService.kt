package tierMaker.application.category

import tierMaker.domain.model.Category
import tierMaker.domain.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun getAllCategory(): List<Category> = categoryRepository.findAll()

    fun getCategoryById(id: String): Category? = categoryRepository.findById(id).orElse(null)

    fun addCategory(adCategoryDto: addCategoryDto, image: MultipartFile): Category {
        // DTOとファイルからドメインオブジェクトを作成する
        val category = categoryFactory.createFromDtoAndFile(categoryDto, file)
        return categoryRepository.save(category)
    }

    fun deleteCategory(id: String) = categoryRepository.deleteById(id)
}
