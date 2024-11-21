package rankifyHub.category.domain.model

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
 */
@Entity
@Table(name = "item")
data class Item(
  @Id val id: String = UUID.randomUUID().toString(),
  @Column(nullable = false) val name: String = "",
  @Lob @Column(name = "image", columnDefinition = "BLOB") val image: ByteArray? = null
) {

  companion object {
    fun create(name: String, image: ByteArray?): Item {
      return Item(name = name, image = image)
    }
  }

  fun update(name: String, image: ByteArray?): Item {
    return Item(id = this.id, name = name, image = image)
  }

  override fun toString(): String {
    return "Item(id='$id', name='$name')"
  }
}
