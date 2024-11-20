package rankifyHub.userTier.domain.model

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserTierFactory {

    /**
     * UserTierを作成するファクトリーメソッド
     *
     * @param anonymousId 匿名ユーザーの識別子
     * @param categoryId 紐づくカテゴリID
     * @param name UserTierの名前
     * @param isPublic 公開設定
     * @param levels UserTierに含まれるレベルとアイテムのリスト
     * @return 新規作成されたUserTier
     */
    fun create(
        anonymousId: AnonymousId,
        categoryId: UUID,
        name: UserTierName,
        isPublic: Boolean,
        levels: List<Pair<UserTierLevelData, List<UserTierItemData>>>
    ): UserTier {
        // UserTierの初期化
        val userTier = UserTier(
            anonymousId = anonymousId,
            categoryId = categoryId,
            name = name,
            isPublic = isPublic,
            accessUrl = AccessUrl(UUID.randomUUID().toString())
        )

        // 各レベルとアイテムをUserTierに追加（フロントエンドの順序で処理）
        levels.sortedBy { it.first.orderIndex }.forEach { (levelData, itemDataList) ->
            val level = userTier.addLevel(levelData.name)
            itemDataList.sortedBy { it.orderIndex }.forEach { itemData ->
                userTier.addItemToLevel(level.id, itemData.itemId)
            }
        }

        return userTier
    }
}

/**
 * UserTierLevelのデータクラス
 *
 * @property name レベルの名前
 * @property orderIndex レベルの並び順（フロントエンドからの入力順序）
 */
data class UserTierLevelData(
    val name: UserTierName,
    val orderIndex: Int
)

/**
 * UserTierItemのデータクラス
 *
 * @property itemId アイテムのID
 * @property orderIndex アイテムの並び順（フロントエンドからの入力順序）
 */
data class UserTierItemData(
    val itemId: UUID,
    val orderIndex: Int
)
