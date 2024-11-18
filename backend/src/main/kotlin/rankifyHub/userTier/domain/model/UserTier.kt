package rankifyHub.userTier.domain.model

import jakarta.persistence.*
import rankifyHub.userTier.domain.vo.AccessUrl
import rankifyHub.userTier.domain.vo.AnonymousId
import java.time.LocalDateTime
import java.util.*

/**
 * ユーザーが作成したTierエンティティ
 *
 * @property id ユニークな識別子（UUID）
 * @property anonymousId 匿名ユーザー識別子
 * @property categoryId 紐づくカテゴリのID
 * @property name Tierの名前
 * @property visibility 公開/非公開の設定（ENUM型）
 * @property accessUrl アクセス用のURL（値オブジェクト）
 * @property createdAt 作成日時
 * @property updatedAt 更新日時
 */
@Entity
@Table(name = "user_tier")
data class UserTier(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Embedded
    val anonymousId: AnonymousId,

    @Column(name = "category_id", nullable = false)
    val categoryId: UUID,

    @Column(name = "name", nullable = false)
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    val visibility: Visibility = Visibility.PRIVATE,

    @Embedded
    val accessUrl: AccessUrl,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    /**
     * 公開設定を変更する
     *
     * @param newVisibility 新しい公開設定
     */
    fun changeVisibility(newVisibility: Visibility): UserTier {
        return this.copy(visibility = newVisibility, updatedAt = LocalDateTime.now())
    }

    /**
     * 名前を更新する
     *
     * @param newName 新しい名前
     */
    fun updateName(newName: String): UserTier {
        return this.copy(name = newName, updatedAt = LocalDateTime.now())
    }

    /**
     * アクセスURLを再生成する
     */
    fun regenerateAccessUrl(): UserTier {
        return this.copy(accessUrl = AccessUrl.generate(), updatedAt = LocalDateTime.now())
    }
}
