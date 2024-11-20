package rankifyHub.userTier.presentation.dto

data class CreateUserTierRequest(
    val anonymousId: String,
    val categoryId: String,
    val name: String,
    val isPublic: Boolean,
    val levels: List<UserTierLevelRequest>
)

data class UserTierLevelRequest(
    val name: String,
    val orderIndex: Int,
    val items: List<UserTierItemRequest>
)

data class UserTierItemRequest(
    val itemId: String,
    val orderIndex: Int
)
