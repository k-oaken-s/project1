package rankifyHub.userTier.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * ユーザーが作成したTierを表すエンティティ
 *
 * @property id Tierの一意な識別子
 * @property anonymousId 匿名ユーザーの識別子
 * @property categoryId Tierが関連付けられるカテゴリーID
 * @property tierName Tierの名前
 * @property order 上からの並び順
 * @property createdAt 作成日時
 * @property updatedAt 更新日時
 * @property items Tierに配置されたアイテムのリスト
 * @property configs Tierに紐づく設定
 */
@Entity
@Table(name = "user_tiers")
data class UserTier(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val anonymousId: String,

    @Column(nullable = false)
    val categoryId: String,

    @Column(nullable = false)
    val tierName: String,

    @Column(nullable = false)
    val order: Int,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "userTier", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: List<UserTierItem> = emptyList(),

    @OneToMany(mappedBy = "userTier", cascade = [CascadeType.ALL], orphanRemoval = true)
    val configs: List<UserTierConfig> = emptyList()
)
