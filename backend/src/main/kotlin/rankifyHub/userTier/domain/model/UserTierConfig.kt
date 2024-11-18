package rankifyHub.userTier.domain.model

import jakarta.persistence.*

/**
 * ユーザーが作成したTierに紐づく設定を表すエンティティ
 *
 * @property id 設定の一意な識別子
 * @property userTier Tierに紐づく親エンティティ
 * @property key 設定のキー
 * @property value 設定の値
 * @property order 設定の並び順
 */
@Entity
@Table(
    name = "user_tier_configs",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_tier_id", "order"])]
)
data class UserTierConfig(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tier_id", nullable = false)
    val userTier: UserTier,

    @Column(nullable = false)
    val key: String,

    @Column(nullable = false)
    val value: String,

    @Column(nullable = false)
    val order: Int
)
