package rankifyHub.userTier.domain.repository

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rankifyHub.userTier.domain.model.UserTier

@Repository
interface UserTierRepository : JpaRepository<UserTier, UUID> {
  /**
   * 匿名ユーザーのIDとカテゴリIDで特定のUserTierを検索
   *
   * @param categoryId カテゴリのID
   * @return 該当するUserTierのリスト
   */
  fun findByCategoryId(categoryId: String): List<UserTier>

  /**
   * 公開されているUserTierを取得
   *
   * @return 公開されているUserTierのリスト
   */
  fun findByIsPublicTrue(): List<UserTier>
}
