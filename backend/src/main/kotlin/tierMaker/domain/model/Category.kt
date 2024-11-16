package tierMaker.domain.model

import jakarta.persistence.*
import java.util.*

/**
 * カテゴリーエンティティ
 *
 * <p>
 * カテゴリー集約のルート
 * </p>
 *
 * @property id カテゴリーの一意な識別子。
 * @property name カテゴリー名。必須フィールド。
 * @property description カテゴリーの説明。省略可能。
 * @property image カテゴリーの画像データ（バイナリ形式）。省略可能。
 * @property items カテゴリーに関連付けられたアイテムのリスト（読み取り専用）。
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

  /**
   * 関連付けられたアイテムの読み取り専用リスト。
   */
  val items: List<Item>
    get() = _items.toList()

  companion object {
    /**
     * カテゴリーを作成するファクトリメソッド。
     *
     * @param name カテゴリー名。
     * @param description カテゴリーの説明（省略可能）。
     * @param image カテゴリーの画像データ（省略可能）。
     * @return 新しいカテゴリーインスタンス。
     */
    fun create(name: String, description: String?, image: ByteArray?): Category {
      return Category(name = name, description = description, image = image)
    }
  }

  /**
   * カテゴリーにアイテムを追加します。
   *
   * @param name アイテム名。
   * @param image アイテムの画像データ（省略可能）。
   * @return 追加されたアイテム。
   */
  fun addItem(name: String, image: ByteArray?): Item {
    val item = Item.create(name = name, category = this, image = image)
    _items.add(item)
    return item
  }

  /**
   * カテゴリー内の既存アイテムを更新します。
   *
   * @param itemId 更新対象のアイテムID。
   * @param name 新しいアイテム名。
   * @param image 新しい画像データ（省略可能）。
   * @param keepCurrentImage 既存の画像を保持する場合は `true`。
   * @return 更新されたアイテム。
   * @throws IllegalArgumentException 対象のアイテムが見つからない場合。
   */
  fun updateItem(itemId: String, name: String, image: ByteArray?, keepCurrentImage: Boolean): Item {
    val item = _items.find { it.id == itemId } ?: throw IllegalArgumentException("Item not found")
    val updatedImage = if (keepCurrentImage) item.image else image
    val updatedItem = item.update(name = name, image = updatedImage)
    _items.removeIf { it.id == itemId }
    _items.add(updatedItem)
    return updatedItem
  }
}
