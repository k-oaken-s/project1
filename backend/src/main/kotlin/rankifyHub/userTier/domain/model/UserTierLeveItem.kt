package rankifyHub.userTier.domain.model

import jakarta.persistence.*
import rankifyHub.userTier.domain.vo.OrderIndex
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "user_tier_level_item",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["user_tier_level_id", "order_index"])
    ]
)
data class UserTierLevelItem(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "item_id", nullable = false)
    val itemId: UUID,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "order_index", nullable = false))
    )
    val orderIndex: OrderIndex,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tier_level_id", nullable = false)
    val userTierLevel: UserTierLevel,
) {
    fun updateOrder(newOrder: OrderIndex) = this.copy(orderIndex = newOrder)
}
