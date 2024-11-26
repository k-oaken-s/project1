package rankifyHub.userTier.presentation.dto

import rankifyHub.userTier.domain.model.UserTier
import rankifyHub.userTier.domain.model.UserTierLevel
import rankifyHub.userTier.domain.model.UserTierLevelItem

data class UserTierResponse(
  val id: String,
  val anonymousId: String,
  val categoryId: String,
  val name: String,
  val isPublic: Boolean,
  val accessUrl: String,
  val levels: List<UserTierLevelResponse>
) {
  companion object {
    fun fromEntity(userTier: UserTier): UserTierResponse {
      return UserTierResponse(
        id = userTier.id.toString(),
        anonymousId = userTier.anonymousId.value,
        categoryId = userTier.categoryId.toString(),
        name = userTier.name.value,
        isPublic = userTier.isPublic,
        accessUrl = userTier.accessUrl.value,
        levels = userTier.getLevels().map { UserTierLevelResponse.fromEntity(it) }
      )
    }
  }
}

data class UserTierLevelResponse(
  val id: String,
  val name: String,
  val order: Int,
  val items: List<UserTierItemResponse>
) {
  companion object {
    fun fromEntity(userTierLevel: UserTierLevel): UserTierLevelResponse {
      return UserTierLevelResponse(
        id = userTierLevel.id.toString(),
        name = userTierLevel.tierName,
        order = userTierLevel.orderIndex.value,
        items = userTierLevel.items.map { UserTierItemResponse.fromEntity(it) }
      )
    }
  }
}

data class UserTierItemResponse(val itemId: String, val order: Int) {
  companion object {
    fun fromEntity(userTierItem: UserTierLevelItem): UserTierItemResponse {
      return UserTierItemResponse(
        itemId = userTierItem.itemId.toString(),
        order = userTierItem.orderIndex.value
      )
    }
  }
}
