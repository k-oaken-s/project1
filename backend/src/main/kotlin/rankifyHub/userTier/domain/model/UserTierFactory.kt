package rankifyHub.userTier.domain.model

import java.util.UUID
import org.springframework.stereotype.Component
import rankifyHub.userTier.domain.vo.AccessUrl
import rankifyHub.userTier.domain.vo.AnonymousId
import rankifyHub.userTier.domain.vo.OrderIndex
import rankifyHub.userTier.domain.vo.UserTierName

@Component
class UserTierFactory {

  /**
   * UserTierを作成するファクトリーメソッド
   *
   * @param anonymousId 匿名ユーザーの識別子
   * @param categoryId 紐づくカテゴリID
   * @param name UserTierの名前
   * @param isPublic 公開設定
   * @param levels UserTierに含まれるレベルデータのリスト
   * @return 新規作成されたUserTier
   */
  fun create(
    anonymousId: AnonymousId,
    categoryId: UUID,
    name: UserTierName,
    isPublic: Boolean,
    levels: List<UserTierLevelData>
  ): UserTier {
    // UserTierの初期化
    val userTier =
      UserTier(
        anonymousId = anonymousId,
        categoryId = categoryId,
        name = name,
        isPublic = isPublic,
        accessUrl = AccessUrl(UUID.randomUUID().toString())
      )

    // 各レベルとアイテムをUserTierに追加（フロントエンドの順序で処理）
    levels
      .sortedBy { it.orderIndex.value }
      .forEach { levelData ->
        val level =
          UserTierLevel(
            userTier = userTier,
            name = levelData.name.value,
            orderIndex = levelData.orderIndex
          )
        userTier.addLevel(level)
        levelData.items
          .sortedBy { it.orderIndex.value }
          .forEach { itemData ->
            level.addItem(
              UserTierLevelItem(
                level.id,
                itemData.itemId,
                OrderIndex(itemData.orderIndex.value),
                userTierLevel = level
              )
            )
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
 * @property items レベルに紐づくアイテムのリスト
 */
data class UserTierLevelData(
  val name: UserTierName,
  val orderIndex: OrderIndex,
  val items: List<UserTierItemData>
)

/**
 * UserTierItemのデータクラス
 *
 * @property itemId アイテムのID
 * @property orderIndex アイテムの並び順（フロントエンドからの入力順序）
 */
data class UserTierItemData(val itemId: UUID, val orderIndex: OrderIndex)
