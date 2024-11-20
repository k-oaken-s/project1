package rankifyHub.userTier.domain.model

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "user_tier_level",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["user_tier_id", "order_index"])
    ]
)
data class UserTierLevel(
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tier_id", nullable = false)
    val userTier: UserTier,

    @Column(name = "tier_name", nullable = false)
    val tierName: String,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "order_index", nullable = false))
    )
    val orderIndex: OrderIndex,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now(),

    @OneToMany(mappedBy = "userTierLevel", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<UserTierItem> = mutableListOf()
) {
    fun getItems(): List<UserTierItem> = items.toList()

    fun addItem(item: UserTierItem) {
        val nextOrder = items.maxOfOrNull { it.order.value }?.plus(1) ?: 1
        items.add(item.copy(order = OrderIndex(nextOrder), userTierLevel = this))
    }

    fun removeItem(item: UserTierItem) {
        items.remove(item)
        reorderItems()
    }

    private fun reorderItems() {
        items.sortBy { it.order.value }
        items.forEachIndexed { index, item ->
            item.updateOrder(OrderIndex(index + 1))
        }
    }

    fun updateOrder(newOrder: OrderIndex) {
        order = newOrder
    }
}
