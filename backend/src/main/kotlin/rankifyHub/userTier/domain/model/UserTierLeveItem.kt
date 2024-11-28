package rankifyHub.userTier.domain.model

import jakarta.persistence.*
import rankifyHub.userTier.domain.vo.OrderIndex
import java.time.Instant
import java.util.*

@Entity
@Table(
    name = "user_tier_level_item",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_tier_level_id", "order_index"])]
)
class UserTierLevelItem(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tier_level_id", nullable = false)
    var userTierLevel: UserTierLevel? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tier_id", nullable = false)
    var userTier: UserTier? = null,

    @Column(name = "item_id", nullable = false)
    var itemId: String,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "order_index", nullable = false))
    )
    var orderIndex: OrderIndex = OrderIndex(1),

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now()
) {
    constructor() : this(itemId = "")

    fun updateOrder(newOrder: OrderIndex) {
        this.orderIndex = newOrder
    }
}
