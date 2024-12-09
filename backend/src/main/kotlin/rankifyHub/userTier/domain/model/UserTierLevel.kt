package rankifyHub.userTier.domain.model

import jakarta.persistence.*
import java.time.Instant
import java.util.*
import rankifyHub.userTier.domain.vo.OrderIndex

@Entity
@Table(
  name = "user_tier_level",
  uniqueConstraints = [UniqueConstraint(columnNames = ["user_tier_id", "order_index"])]
)
class UserTierLevel(
  @Id val id: String = UUID.randomUUID().toString(),
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_tier_id", nullable = false)
  var userTier: UserTier? = null, // var に変更
  @Column(name = "name", nullable = false) val name: String = "",
  @Embedded
  @AttributeOverrides(
    AttributeOverride(name = "value", column = Column(name = "order_index", nullable = false))
  )
  var orderIndex: OrderIndex = OrderIndex(1),
  @Column(name = "created_at", nullable = false) val createdAt: Instant = Instant.now(),
  @Column(name = "updated_at", nullable = false) val updatedAt: Instant = Instant.now(),
  @OneToMany(
    mappedBy = "userTierLevel",
    cascade = [CascadeType.ALL],
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  val items: MutableList<UserTierLevelItem> = mutableListOf()
) {
  constructor() : this(id = UUID.randomUUID().toString())

  fun addItem(item: UserTierLevelItem) {
    val nextOrder = items.maxOfOrNull { it.orderIndex.value }?.plus(1) ?: 1
    item.orderIndex = OrderIndex(nextOrder)
    item.userTierLevel = this
    items.add(item)
  }

  fun removeItem(item: UserTierLevelItem) {
    items.remove(item)
    reorderItems()
  }

  private fun reorderItems() {
    items.sortBy { it.orderIndex.value }
    items.forEachIndexed { index, item -> item.updateOrder(OrderIndex(index + 1)) }
  }

  fun updateOrder(newOrder: OrderIndex) {
    this.orderIndex = newOrder
  }
}
