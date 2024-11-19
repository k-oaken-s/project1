package rankifyHub.userTier.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

/**
 * ユーザーTierに含まれるレベルを表すエンティティ
 *
 * @property id レベルの一意な識別子
 * @property userTier ユーザーTierに紐づく親エンティティ
 * @property tierName レベル名
 * @property tierOrder レベルの順序
 * @property createdAt 作成日時
 * @property updatedAt 更新日時
 * @property items レベルに配置されたアイテムのリスト
 */
@Entity
@Table(
    name = "user_tier_levels",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["user_tier_id", "tier_name"]),
        UniqueConstraint(columnNames = ["user_tier_id", "tier_order"])
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

    @Column(name = "tier_order", nullable = false)
    val tierOrder: Int,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "userTierLevel", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val items: MutableList<UserTierItem> = mutableListOf()
)
