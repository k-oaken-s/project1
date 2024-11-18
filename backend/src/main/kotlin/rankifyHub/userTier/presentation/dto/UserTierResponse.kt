package rankifyHub.userTier.presentation.dto

import rankifyHub.userTier.domain.model.UserTier

data class UserTierResponse(
    val id: Long,
    val anonymousId: String,
    val categoryId: String,
    val tierName: String,
    val order: Int,
    val items: List<UserTierItemResponse>,
    val configs: List<UserTierConfigResponse>
) {
    companion object {
        fun fromEntity(userTier: UserTier): UserTierResponse {
            return UserTierResponse(
                id = userTier.id,
                anonymousId = userTier.anonymousId,
                categoryId = userTier.categoryId,
                tierName = userTier.tierName,
                order = userTier.order,
                items = userTier.items.map { UserTierItemResponse.fromEntity(it) },
                configs = userTier.configs.map { UserTierConfigResponse.fromEntity(it) }
            )
        }
    }
}

data class UserTierItemResponse(
    val itemId: String,
    val order: Int
) {
    companion object {
        fun fromEntity(userTierItem: rankifyHub.userTier.domain.model.UserTierItem): UserTierItemResponse {
            return UserTierItemResponse(
                itemId = userTierItem.itemId,
                order = userTierItem.order
            )
        }
    }
}

data class UserTierConfigResponse(
    val key: String,
    val value: String,
    val order: Int
) {
    companion object {
        fun fromEntity(userTierConfig: rankifyHub.userTier.domain.model.UserTierConfig): UserTierConfigResponse {
            return UserTierConfigResponse(
                key = userTierConfig.key,
                value = userTierConfig.value,
                order = userTierConfig.order
            )
        }
    }
}
