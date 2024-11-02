package tierMaker.Presentation.controller

import tierMaker.application.category.ItemService
import tierMaker.domain.model.Item
import tierMaker.Presentation.controller.dto.AddItemRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categories/{categoryId}/items")
class ItemController(private val itemService: ItemService) {

    @GetMapping
    fun getItemsByCategoryId(@PathVariable categoryId: String): List<Item> {
        return itemService.getItemsByCategoryId(categoryId)
    }

    @PostMapping
    fun addItem(@PathVariable categoryId: String, @RequestBody addItemRequest: AddItemRequest): Item {
        return itemService.addItemToCategory(categoryId, addItemRequest.name)
    }
}
