package tierMaker.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import tierMaker.Presentation.controller.dto.AddItemRequest
import tierMaker.domain.model.Item
import tierMaker.application.category.ItemUseCase

@RestController
@RequestMapping("/categories/{categoryId}/items")
class ItemController(
    private val itemUseCase: ItemUseCase
) {

    @GetMapping
    fun getItemsByCategoryId(@PathVariable categoryId: String): ResponseEntity<List<Item>> {
        val items = itemUseCase.getItemsByCategoryId(categoryId)
        return ResponseEntity.ok(items)
    }

    @PostMapping
    fun addItemToCategory(
        @PathVariable categoryId: String,
        @RequestPart("file") file: MultipartFile?,
        @RequestPart("addItemRequest") addItemRequest: AddItemRequest
    ): ResponseEntity<Item> {
        val item = itemUseCase.addItemToCategory(categoryId, addItemRequest.name, file?.bytes)
        return ResponseEntity.ok(item)
    }
}
