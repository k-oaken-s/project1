package rankifyHub.userTier.domain.model

import jakarta.persistence.*
import java.time.Instant
import java.util.*
import rankifyHub.userTier.domain.vo.OrderIndex

@Entity
@Table(
  name = "user_tier_level_item",
  uniqueConstraints = [UniqueConstraint(columnNames = ["user_tier_level_id", "order_index"])]
)
open class UserTierLevelItem(
  @Id val id: UUID = UUID.randomUUID(),
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_tier_level_id", nullable = false)
  var userTierLevel: UserTierLevel,
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_tier_id", nullable = false)
  val userTier: UserTier,
  @Column(name = "item_id", nullable = false) val itemId: String,
  @Embedded
  @AttributeOverrides(
    AttributeOverride(name = "value", column = Column(name = "order_index", nullable = false))
  )
  var orderIndex: OrderIndex,
  @Column(name = "created_at", nullable = false) val createdAt: Instant,
  @Column(name = "updated_at", nullable = false) var updatedAt: Instant
) {
  constructor(
    userTierLevel: UserTierLevel,
    userTier: UserTier,
    itemId: String,
    orderIndex: OrderIndex,
    createdAt: Instant = Instant.now(),
    updatedAt: Instant = Instant.now()
  ) : this(
    id = UUID.randomUUID(),
    userTierLevel = userTierLevel,
    userTier = userTier,
    itemId = itemId,
    orderIndex = orderIndex,
    createdAt = createdAt,
    updatedAt = updatedAt
  )

  fun updateOrder(newOrder: OrderIndex) {
    this.orderIndex = newOrder
    this.updatedAt = Instant.now()
  }
}
