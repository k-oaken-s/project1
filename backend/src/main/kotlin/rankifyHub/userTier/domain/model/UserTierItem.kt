package rankifyHub.userTier.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

/**
 * ユーザーTierの特定のレベルに配置されたアイテムを表すエンティティ
 *
 * @property id アイテムの一意な識別子
 * @property userTierLevel ユーザーTierレベルに紐づく親エンティティ
 * @property itemId アイテムの一意な識別子
 * @property order レベル内での並び順
 * @property createdAt 作成日時
 * @property updatedAt 更新日時
 */
@Entity
@Table(
    name = "user_tier_items",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_tier_level_id", "order"])]
)
data class UserTierItem(
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tier_level_id", nullable = false)
    val userTierLevel: UserTierLevel,

    @Column(name = "item_id", nullable = false)
    val itemId: UUID,

    @Column(name = "order", nullable = false)
    val order: Int,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
