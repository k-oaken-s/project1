package rankifyHub.category.domain.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.util.*

/**
 * アイテムエンティティ
 *
 * <p>
 * 集約内エンティティのため直接は使用しない HACK: 直接使用されないようにしたい </p>
 *
 * @property id アイテムの一意な識別子。
 * @property name アイテム名。必須フィールド。
 * @property image アイテムの画像データ（バイナリ形式）。省略可能。
 * @property category このアイテムが属するカテゴリー。
 * @property description アイテムの詳細情報。省略可能。
 */
@Entity
@Table(name = "item")
open class Item(
  @Id
  @GeneratedValue
  @Column(columnDefinition = "UUID", updatable = false, nullable = false)
  val id: UUID = UUID.randomUUID(),
  @Column(nullable = false) var name: String = "",
  @Lob var image: ByteArray? = null,
  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  @JsonBackReference
  var category: Category? = null,
  @Column(name = "description", nullable = true, length = 255) var description: String? = null,
) {
  // デフォルトコンストラクタ（Hibernate用）
  protected constructor() : this(name = "", category = null)

  companion object {
    fun create(
      name: String,
      image: ByteArray?,
      category: Category,
      description: String? = null
    ): Item {
      return Item(
        id = UUID.randomUUID(),
        name = name,
        image = image,
        category = category,
        description = description
      )
    }
  }

  fun update(name: String, image: ByteArray?, description: String? = null): Item {
    this.name = name
    this.image = image
    this.description = description
    return this
  }

  override fun toString(): String {
    return "Item(id=$id, name='$name', description=$description, category=${category?.id})"
  }
}
