package rankifyHub.userTier.domain.model

import jakarta.persistence.*

/**
 * ユーザーが作成したTierに配置されたアイテムを表すエンティティ
 *
 * @property id アイテムの一意な識別子
 * @property userTier Tierに紐づく親エンティティ
 * @property itemId アイテムのID
 * @property order Tier内での並び順
 */
@Entity
@Table(
    name = "user_tier_items",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_tier_id", "order"])]
)
data class UserTierItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tier_id", nullable = false)
    val userTier: UserTier,

    @Column(nullable = false)
    val itemId: String,

    @Column(nullable = false)
    val order: Int
)
