package rankifyHub.userTier.domain.model

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

/**
 * ユーザーのTier内に配置されたアイテムを表すエンティティ
 *
 * @property id UUIDv7を使用した一意な識別子
 * @property userTierLevel 紐づくUserTierLevelのエンティティ
 * @property itemId 紐づくアイテムのID
 * @property order 並び順
 * @property createdAt 作成日時
 * @property updatedAt 更新日時
 */
@Entity
@Table(
    name = "user_tier_level_items",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["user_tier_level_id", "order"])
    ]
)
data class UserTierItem(
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tier_level_id", nullable = false)
    val userTierLevel: UserTierLevel,

    @Column(name = "item_id", nullable = false)
    val itemId: UUID,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "order_index", nullable = false))
    )
    val order: OrderIndex,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tier_level_id", nullable = false)
    val userTierLevel: UserTierLevel,

    @Column(name = "item_id", nullable = false)
    val itemId: UUID,
) {
    /**
     * orderを更新する
     */
    fun updateOrder(newOrder: Order) = this.copy(order = newOrder)
}
