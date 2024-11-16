package tierMaker.domain.model

import jakarta.persistence.*
import java.util.*

/**
 * アイテムエンティティ
 *
 * <p>
 * 集約内エンティティのため直接は使用しない
 * HACK: 直接使用されないようにしたい
 * </p>
 *
 * @property id アイテムの一意な識別子。
 * @property name アイテム名。必須フィールド。
 * @property image アイテムの画像データ（バイナリ形式）。省略可能。
 * @property category このアイテムが属するカテゴリー。
 */
@Entity
@Table(name = "item")
data class Item(
  @Id
  val id: String = UUID.randomUUID().toString(),

  @Column(nullable = false)
  val name: String = "",

  @Lob
  @Column(name = "image", columnDefinition = "BLOB")
  val image: ByteArray? = null,

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  val category: Category
) {

  /**
   * デフォルトコンストラクタ。
   *
   * データベース操作のために必要。
   */
  constructor() : this(UUID.randomUUID().toString(), "", null, Category())

  companion object {
    /**
     * アイテムを作成するファクトリメソッド。
     *
     * @param name アイテム名。
     * @param category このアイテムが属するカテゴリー。
     * @param image アイテムの画像データ（省略可能）。
     * @return 新しいアイテムインスタンス。
     */
    fun create(name: String, category: Category, image: ByteArray?): Item {
      return Item(name = name, image = image, category = category)
    }
  }

  /**
   * アイテムを更新します。
   *
   * @param name 更新後のアイテム名。
   * @param image 更新後の画像データ（省略可能）。
   * @return 更新されたアイテムインスタンス。
   */
  fun update(name: String, image: ByteArray?): Item {
    return Item(id = this.id, name = name, image = image, category = this.category)
  }
}
