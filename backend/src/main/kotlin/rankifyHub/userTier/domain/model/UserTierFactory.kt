package rankifyHub.userTier.domain.model

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserTierFactory {

    fun create(
        anonymousId: AnonymousId,
        categoryId: UUID,
        name: UserTierName,
        isPublic: Boolean,
        levels: List<Pair<UserTierName, List<UUID>>>
    ): UserTier {
        val userTier = UserTier(
            anonymousId = anonymousId,
            categoryId = categoryId,
            name = name,
            isPublic = isPublic,
            accessUrl = AccessUrl(UUID.randomUUID().toString())
        )

        levels.forEach { (levelName, itemIds) ->
            val level = userTier.addLevel(levelName)
            itemIds.forEach { itemId ->
                userTier.addItemToLevel(level.id, itemId)
            }
        }

        return userTier
    }
}
