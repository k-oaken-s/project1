package rankifyHub.category.domain.model

import jakarta.persistence.*
import java.util.*

/**
 * カテゴリーエンティティ。
 *
 * <p>
 * カテゴリー集約のルート </p>
 *
 * @property id カテゴリーの一意な識別子。
 * @property name カテゴリー名。必須フィールド。
 * @property description カテゴリーの説明。省略可能。
 * @property image カテゴリーの画像データ（バイナリ形式）。省略可能。
 * @property items カテゴリーに関連付けられたアイテムのリスト（読み取り専用）。
 */
@Entity
@Table(name = "category")
open class Category(
  @Id
  @GeneratedValue
  @Column(columnDefinition = "UUID", updatable = false, nullable = false)
  val id: UUID = UUID.randomUUID(),
  @Column(nullable = false) val name: String = "",
  val description: String? = null,
  @Lob val image: ByteArray? = null,
  @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
  @JoinColumn(name = "category_id")
  private val _items: MutableList<Item> = mutableListOf()
) {

  val items: List<Item>
    get() = _items.toList()

  override fun toString(): String {
    return "Category(id=$id, name='$name', description=$description)"
  }

  companion object {
    fun create(name: String, description: String?, image: ByteArray?): Category {
      return Category(id = UUID.randomUUID(), name = name, description = description, image = image)
    }
  }

  fun addItem(name: String, image: ByteArray?, description: String?): Item {
    val item = Item.create(name = name, image = image, category = this, description = description)
    _items.add(item)
    return item
  }

  fun updateItem(
    itemId: UUID,
    name: String,
    image: ByteArray?,
    keepCurrentImage: Boolean,
    description: String?
  ): Item {
    val item = _items.find { it.id == itemId } ?: throw IllegalArgumentException("Item not found")
    val updatedImage = if (keepCurrentImage) item.image else image
    val updatedItem = item.update(name = name, image = updatedImage, description = description)
    _items.removeIf { it.id == itemId }
    _items.add(updatedItem)
    return updatedItem
  }
}
