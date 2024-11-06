package tierMaker.presentation.controller

import tierMaker.application.category.CategoryUseCase
import tierMaker.application.category.AddCategoryDto
import tierMaker.presentation.controller.dto.CategoryResponse
import tierMaker.presentation.controller.dto.ItemResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.Base64

@RestController
@RequestMapping("/categories")
class CategoryController(
    private val categoryUseCase: CategoryUseCase
) {
    @GetMapping
    fun getAllCategories(): ResponseEntity<List<CategoryResponse>> {
        val categories = categoryUseCase.getAllCategories()
        val response = categories.map { category ->
            CategoryResponse(
                id = category.id,
                name = category.name,
                description = category.description,
                image = category.image?.let { Base64.getEncoder().encodeToString(it) }
            )
        }
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{categoryId}")
    fun getCategoryWithItems(@PathVariable categoryId: String): ResponseEntity<CategoryResponse> {
        val category = categoryUseCase.getCategoryWithItems(categoryId)
        val response = CategoryResponse(
            id = category.id,
            name = category.name,
            description = category.description,
            image = category.image?.let { Base64.getEncoder().encodeToString(it) },
            items = category.items.map { item ->
                ItemResponse(
                    id = item.id,
                    name = item.name,
                    image = item.image?.let { Base64.getEncoder().encodeToString(it) }
                )
            }
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun addCategory(
        @RequestPart("category") categoryDto: AddCategoryDto,
        @RequestPart("file", required = false) file: MultipartFile?
    ): ResponseEntity<CategoryResponse> {
        val category = categoryUseCase.addCategory(categoryDto, file?.bytes)
        val response = CategoryResponse(
            id = category.id,
            name = category.name,
            description = category.description,
            image = category.image?.let { Base64.getEncoder().encodeToString(it) }
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/{categoryId}/items")
    fun addItemToCategory(
        @PathVariable categoryId: String,
        @RequestPart("item") itemJson: String,
        @RequestPart(value = "file", required = false) file: MultipartFile?
    ): ResponseEntity<ItemResponse> {
        val item = categoryUseCase.addItemToCategory(categoryId, itemJson, file?.bytes)
        val response = ItemResponse(
            id = item.id,
            name = item.name,
            image = item.image?.let { Base64.getEncoder().encodeToString(it) }
        )
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{categoryId}/items/{itemId}")
    fun updateItemInCategory(
        @PathVariable categoryId: String,
        @PathVariable itemId: String,
        @RequestPart("item") itemJson: String,
        @RequestPart(value = "file", required = false) file: MultipartFile?,
        @RequestParam(value = "keepCurrentImage", defaultValue = "false") keepCurrentImage: Boolean
    ): ResponseEntity<ItemResponse> {
        val updatedItem = categoryUseCase.updateItemInCategory(categoryId, itemId, itemJson, file, keepCurrentImage)
        val response = ItemResponse(
            id = updatedItem.id,
            name = updatedItem.name,
            image = updatedItem.image?.let { Base64.getEncoder().encodeToString(it) }
        )
        return ResponseEntity.ok(response)
    }
}
