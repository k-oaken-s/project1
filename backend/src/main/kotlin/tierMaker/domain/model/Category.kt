package tierMaker.domain.model

import jakarta.persistence.*
import java.util.*

/**
 * カテゴリー
 *
 * カテゴリー集約のルート
 */
@Entity
@Table(name = "category")
data class Category(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    val name: String = "",

    val description: String? = null,

    @Lob
    val image: ByteArray? = null,

    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _items: MutableList<Item> = mutableListOf()
) {
    val items: List<Item>
        get() = _items.toList()

    companion object {
        fun create(name: String, description: String?, image: ByteArray?): Category {
            return Category(
                name = name,
                description = description,
                image = image
            )
        }
    }

    fun addItem(name: String, image: ByteArray?): Item {
        val item = Item.create(name = name, category = this, image = image)
        _items.add(item)
        return item
    }

    fun updateItem(itemId: String, name: String, image: ByteArray?, keepCurrentImage: Boolean): Item {
        val item = _items.find { it.id == itemId } ?: throw IllegalArgumentException("Item not found")
        val updatedImage = if (keepCurrentImage) item.image else image
        val updatedItem = item.update(name = name, image = updatedImage)
        _items.removeIf { it.id == itemId }
        _items.add(updatedItem)
        return updatedItem
    }
}
