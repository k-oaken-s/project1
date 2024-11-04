package tierMaker.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import tierMaker.domain.model.Item
import tierMaker.application.category.ItemUseCase
import tierMaker.application.category.addItemDto
import tierMaker.presentation.controller.dto.ItemResponse
import java.util.Base64

@RestController
@RequestMapping("/categories/{categoryId}/items")
class ItemController(
    private val itemUseCase: ItemUseCase
) {

    @GetMapping
    fun getItemsByCategoryId(@PathVariable categoryId: String): ResponseEntity<List<ItemResponse>> {
        val items = itemUseCase.getItemsByCategoryId(categoryId).map { item ->
            ItemResponse(
                id = item.id,
                name = item.name,
                image = item.image?.let { Base64.getEncoder().encodeToString(it) }
            )
        }
        return ResponseEntity.ok(items)
    }

    @PostMapping
    fun addItemToCategory(
        @PathVariable categoryId: String,
        @RequestPart("file") file: MultipartFile?,
        @RequestPart("item") item: addItemDto
    ): ResponseEntity<Item> {
        val item = itemUseCase.addItemToCategory(categoryId, item.name, file?.bytes)
        return ResponseEntity.ok(item)
    }
}