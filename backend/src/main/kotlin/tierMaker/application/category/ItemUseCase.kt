package tierMaker.application.category

import tierMaker.domain.model.Item
import tierMaker.domain.repository.CategoryRepository
import tierMaker.domain.repository.ItemRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ItemUseCase(
    private val itemRepository: ItemRepository,
    private val categoryUseCase: CategoryUseCase,
    private val categoryRepository: CategoryRepository,
) {

    fun getItemsByCategoryId(categoryId: String): List<Item> =
        itemRepository.findByCategoryId(categoryId)

    fun deleteItem(id: String) = itemRepository.deleteById(id)

    @Transactional
    fun addItemToCategory(categoryId: String, itemName: String, imageBytes: ByteArray?): Item {
        val category = categoryRepository.findById(categoryId)
            .orElseThrow { IllegalArgumentException("Category not found") }
        val newItem = Item.create(name = itemName, category = category, image = imageBytes)
        return itemRepository.save(newItem)
    }

    fun updateItem(categoryId: String, itemId: String, name: String, file: MultipartFile?, keepCurrentImage: Boolean): Item {
        val item = itemRepository.findById(itemId).orElseThrow { RuntimeException("Item not found") }
        val imageBytes = if (keepCurrentImage) {
            item.image // 既存の画像を保持
        } else {
            file?.bytes ?: run {
                if (file != null && file.isEmpty) {
                    throw RuntimeException("Failed to read file")
                }
                null
            }
        }
        val updatedItem = item.update(name = name, image = imageBytes)
        return itemRepository.save(updatedItem)
    }
}
