package rankifyHub.userTier.application

import java.time.Instant
import org.springframework.stereotype.Service
import rankifyHub.category.domain.repository.CategoryRepository
import rankifyHub.userTier.domain.repository.UserTierRepository
import rankifyHub.userTier.presentation.dto.UserTierWithCategoryDto

@Service
class GetLatestUserTiersUseCase(
  private val userTierRepository: UserTierRepository,
  private val categoryRepository: CategoryRepository
) {
  /**
   * 公開されているUserTierとそのカテゴリ情報を取得します。
   *
   * @return 公開されているUserTierのリスト
   */
  fun getPublicUserTiers(): List<UserTierWithCategoryDto> {
    val userTiers = userTierRepository.findByIsPublicTrueOrderByCreatedAtDesc()
    return userTiers.mapNotNull { userTier ->
      val category = categoryRepository.findById(userTier.categoryId).orElse(null)
      category?.let {
        UserTierWithCategoryDto(
          userTier = userTier,
          categoryName = it.name,
          categoryImage = it.image
        )
      }
    }
  }

  /**
   * 指定された件数の最新のUserTierとそのカテゴリ情報を取得します。
   *
   * @param limit 取得する件数
   * @return 最新のUserTierリスト
   */
  fun getLatestUserTiers(limit: Int): List<UserTierWithCategoryDto> {
    val userTiers = userTierRepository.findLatest(limit)
    return userTiers.mapNotNull { userTier ->
      val category = categoryRepository.findById(userTier.categoryId).orElse(null)
      category?.let {
        UserTierWithCategoryDto(
          userTier = userTier,
          categoryName = it.name,
          categoryImage = it.image
        )
      }
    }
  }

  /**
   * 指定された時刻以降に作成されたUserTierとそのカテゴリ情報を取得します。
   *
   * @param timestamp 開始時刻
   * @return 指定時刻以降のUserTierリスト
   */
  fun getUserTiersSince(timestamp: Instant): List<UserTierWithCategoryDto> {
    val userTiers = userTierRepository.findSince(timestamp)
    return userTiers.mapNotNull { userTier ->
      val category = categoryRepository.findById(userTier.categoryId).orElse(null)
      category?.let {
        UserTierWithCategoryDto(
          userTier = userTier,
          categoryName = it.name,
          categoryImage = it.image
        )
      }
    }
  }
}
