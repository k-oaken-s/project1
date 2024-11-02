package tierMaker.Presentation.controller

import tierMaker.application.category.CategoryService
import tierMaker.application.category.addCategoryDto
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
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping fun getAllGames(): List<Category> = categoryService.getAllCategory()

    @GetMapping("/{categoryId}")
    fun getGameById(@PathVariable categoryId: String): ResponseEntity<Category> {
        val category = categoryService.getCategoryById(categoryId)
        return if (category != null) ResponseEntity.ok(category) else ResponseEntity.notFound().build()
    }

    @PostMapping(value = ["/upload"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun addCategory(
        @RequestPart("category") addCategoryDto: addCategoryDto,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<Category> {
        val createdCategory = categoryService.addCategory(addCategoryDto, file)
        return ResponseEntity.ok(createdCategory)
    }

}
