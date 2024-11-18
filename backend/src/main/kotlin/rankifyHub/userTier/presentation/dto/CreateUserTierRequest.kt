package rankifyHub.userTier.presentation.dto

data class CreateUserTierRequest(
    val anonymousId: String,
    val categoryId: String,
    val tierName: String,
    val order: Int,
    val items: List<UserTierItemRequest>,
    val configs: List<UserTierConfigRequest>
)

data class UserTierItemRequest(
    val itemId: String
)

data class UserTierConfigRequest(
    val key: String,
    val value: String
)
