package tierMaker.Presentation.controller

import tierMaker.application.category.CategoryUseCase
import tierMaker.application.category.AddCategoryDto
import tierMaker.domain.model.Category
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/categories")
class CategoryController(private val categoryUseCase: CategoryUseCase) {

    @GetMapping fun getAllGames(): List<Category> = categoryUseCase.getAllCategory()

    @GetMapping("/{categoryId}")
    fun getGameById(@PathVariable categoryId: String): ResponseEntity<Category> {
        val category = categoryUseCase.getCategoryById(categoryId)
        return if (category != null) ResponseEntity.ok(category) else ResponseEntity.notFound().build()
    }

    @PostMapping()
    fun addCategory(
        @RequestPart("category") addCategoryDto: AddCategoryDto,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<Category> {
        val imageBytes = if (!file.isEmpty) file.bytes else null
        val createdCategory = categoryUseCase.addCategory(addCategoryDto, imageBytes)
        return ResponseEntity.ok(createdCategory)
    }

}
