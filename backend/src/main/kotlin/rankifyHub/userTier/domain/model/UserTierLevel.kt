package rankifyHub.userTier.domain.model

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

/**
 * ユーザーが作成したTier内のレベル（例: Sランク, Aランクなど）を表すエンティティ
 *
 * @property id UUIDv7を使用した一意な識別子
 * @property userTier 紐づくUserTierのエンティティ
 * @property tierName Tierの名前
 * @property tierOrder 並び順
 * @property createdAt 作成日時
 * @property updatedAt 更新日時
 */
@Entity
@Table(
    name = "user_tier_levels",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["user_tier_id", "tier_name"]),
        UniqueConstraint(columnNames = ["user_tier_id", "tier_order"])
    ]
)
data class UserTierLevel(
    @Id
    val id: UUID = UUID.randomUUID(), // UUIDv7対応ライブラリで生成する想定

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tier_id", nullable = false)
    val userTier: UserTier,

    @Column(name = "name", nullable = false)
    val tierName: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tier_id", nullable = false)
    val userTier: UserTier,

    @OneToMany(mappedBy = "userTierLevel", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<UserTierItem> = mutableListOf(),

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "order_index", nullable = false))
    )
    val order: OrderIndex,
) {
    /**
     * 新しいアイテムを追加し、orderを自動計算
     */
    fun addItem(item: UserTierItem) {
        val nextOrder = items.maxOfOrNull { it.order }?.value?.plus(1) ?: 1
        val newItem = item.copy(order = Order(nextOrder))
        items.add(newItem)
    }

    /**
     * アイテムを削除し、orderを再計算
     */
    fun removeItem(item: UserTierItem) {
        items.remove(item)
        reorderItems()
    }

    /**
     * itemsのorderを再計算
     */
    private fun reorderItems() {
        items.sortBy { it.order.value }
        items.forEachIndexed { index, item ->
            item.updateOrder(Order(index + 1))
        }
    }

    /**
     * orderを更新する
     */
    fun updateOrder(newOrder: Order) = this.copy(order = newOrder)
}
