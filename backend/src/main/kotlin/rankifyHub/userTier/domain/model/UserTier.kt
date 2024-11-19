package rankifyHub.userTier.domain.model

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

/**
 * ユーザーが作成したTierを表すエンティティ
 *
 * @property id UUIDv7を使用した一意な識別子
 * @property anonymousId 匿名ユーザーの識別子
 * @property categoryId 紐づくカテゴリのID
 * @property name Tierの名前
 * @property isPublic Tierの公開設定
 * @property accessUrl アクセスURL（Tierの一意な公開用識別子）
 * @property createdAt 作成日時
 * @property updatedAt 更新日時
 */
@Entity
@Table(name = "user_tier")
data class UserTier(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Embedded
    @Column(name = "anonymous_id", nullable = false)
    val anonymousId: AnonymousId,

    @Column(name = "category_id", nullable = false)
    val categoryId: UUID,

    @Embedded
    @Column(name = "name", nullable = false)
    val name: UserTierName,

    @Column(name = "is_public", nullable = false)
    val isPublic: Visibility,

    @Embedded
    @Column(name = "access_url", nullable = false)
    val accessUrl: AccessUrl,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now()

    @OneToMany(mappedBy = "userTier", cascade = [CascadeType.ALL], orphanRemoval = true)
    val levels: MutableList<UserTierLevel> = mutableListOf(),
) {
    /**
     * 新しいレベルを追加し、orderを自動計算
     */
    fun addLevel(level: UserTierLevel) {
        val nextOrder = levels.maxOfOrNull { it.order }?.value?.plus(1) ?: 1
        val newLevel = level.copy(order = Order(nextOrder))
        levels.add(newLevel)
    }

    /**
     * レベルを削除し、orderを再計算
     */
    fun removeLevel(level: UserTierLevel) {
        levels.remove(level)
        reorderLevels()
    }

    /**
     * levelsのorderを再計算
     */
    private fun reorderLevels() {
        levels.sortBy { it.order.value }
        levels.forEachIndexed { index, level ->
            level.updateOrder(Order(index + 1))
        }
    }
}
