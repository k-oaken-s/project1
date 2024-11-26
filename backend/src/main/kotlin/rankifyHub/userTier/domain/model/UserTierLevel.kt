package rankifyHub.userTier.domain.model

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID
import rankifyHub.userTier.domain.vo.OrderIndex

@Entity
@Table(
  name = "user_tier_level",
  uniqueConstraints = [UniqueConstraint(columnNames = ["user_tier_id", "order_index"])]
)
data class UserTierLevel(
  @Id val id: UUID = UUID.randomUUID(),
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_tier_id", nullable = false)
  val userTier: UserTier? = null,
    @Column(name = "name", nullable = false) val name: String = "",
    @Embedded
  @AttributeOverrides(
    AttributeOverride(name = "value", column = Column(name = "order_index", nullable = false))
  )
  var orderIndex: OrderIndex = OrderIndex(1),
  @Column(name = "created_at", nullable = false) val createdAt: Instant = Instant.now(),
  @Column(name = "updated_at", nullable = false) val updatedAt: Instant = Instant.now(),
  @OneToMany(mappedBy = "userTierLevel", cascade = [CascadeType.ALL], orphanRemoval = true)
  val items: MutableList<UserTierLevelItem> = mutableListOf()
) {
  fun retrieveItems(): List<UserTierLevelItem> = items.toList()

  fun addItem(item: UserTierLevelItem) {
    val nextOrder = items.maxOfOrNull { it.orderIndex.value }?.plus(1) ?: 1
    items.add(item.copy(orderIndex = OrderIndex(nextOrder), userTierLevel = this))
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
