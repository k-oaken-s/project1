package rankifyHub.userTier.presentation.presenter

import org.springframework.stereotype.Component
import rankifyHub.shared.infrustructure.PublicFileStorageRepository
import rankifyHub.userTier.presentation.dto.UserTierResponse
import rankifyHub.userTier.presentation.dto.UserTierWithCategoryDto

@Component
class UserTierPresenter(private val publicFileStorageRepository: PublicFileStorageRepository) {

  /**
   * UserTierWithCategoryDtoをUserTierResponseに変換します。
   *
   * @param dto UserTierとCategory情報をまとめたDTO
   * @return クライアントに返却するレスポンスDTO
   */
  fun toResponse(dto: UserTierWithCategoryDto): UserTierResponse {
    val categoryImageUrl =
      dto.categoryImage?.let { imageBytes ->
        publicFileStorageRepository.saveFile(
          "images/categories",
          dto.userTier.categoryId,
          imageBytes,
          "jpg"
        )
      }
        ?: ""

    return UserTierResponse(
      accessUrl = dto.userTier.accessUrl.value,
      createdAt = dto.userTier.createdAt,
      name = dto.userTier.name.value,
      categoryName = dto.categoryName,
      categoryImageUrl = categoryImageUrl
    )
  }
}
