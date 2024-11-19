package rankifyHub.userTier.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

/**
 * ユーザーが作成したTierのエンティティ
 *
 * @property id ユーザーTierの一意な識別子
 * @property anonymousId 匿名ユーザー識別子
 * @property categoryId カテゴリの一意な識別子
 * @property name Tierの名前
 * @property isPublic 公開・非公開のフラグ
 * @property accessUrl アクセス用の一意なURL
 * @property createdAt 作成日時
 * @property updatedAt 更新日時
 * @property levels ユーザーTierに紐づくレベルのリスト
 */
@Entity
@Table(name = "user_tier")
data class UserTier(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "anonymous_id", nullable = false)
    val anonymousId: AnonymousId,

    @Column(name = "category_id", nullable = false)
    val categoryId: UUID,

    @Column(nullable = false)
    val name: String,

    @Column(name = "is_public", nullable = false)
    val isPublic: Visibility,

    @Column(name = "access_url", nullable = false)
    val accessUrl: AccessUrl,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "userTier", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val levels: MutableList<UserTierLevel> = mutableListOf()
)
