
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
class Item(
  @Id val id: String = UUID.randomUUID().toString(),
  @Column(nullable = false) val name: String = "",
  @Lob @Column(name = "image", columnDefinition = "BLOB") val image: ByteArray? = null,
  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  @JsonBackReference
  val category: Category,
  @Column(name = "description", nullable = true, length = 255) val description: String? = null,

) {
    constructor() : this(id = "", name = "", image = null, category = Category(), description = null)

    companion object {
        fun create(name: String, image: ByteArray?, category: Category, description: String? = null): Item {
            return Item(name = name, image = image, category = category, description = description)
        }
    }

    fun update(name: String, image: ByteArray?, description: String? = null): Item {
        return Item(id = this.id, name = name, image = image, category = this.category, description = description)
    }

    override fun toString(): String {
        return "Item(id='$id', name='$name', description='${description}', category='${category.id}')"
    }
}
