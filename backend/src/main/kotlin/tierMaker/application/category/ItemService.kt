package tierMaker.application.category

import tierMaker.domain.model.Item
import tierMaker.domain.repository.CategoryRepository
import tierMaker.domain.repository.ItemRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ItemService(
    private val itemRepository: ItemRepository,
    private val categoryService: CategoryService,
    private val categoryRepository: CategoryRepository,
) {

    fun getItemsByCategoryId(categoryId: String): List<Item> =
            itemRepository.findByCategoryId(categoryId)

    fun deleteItem(id: String) = itemRepository.deleteById(id)

    @Transactional
    fun addItemToCategory(categoryId: String, itemName: String): Item {
        val category = categoryRepository.findById(categoryId)
            .orElseThrow { IllegalArgumentException("Category not found") }
        val newItem = Item(name = itemName, category = category)
        return itemRepository.save(newItem)
    }
}
