package rankifyHub.userTier.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rankifyHub.userTier.domain.model.*
import rankifyHub.userTier.domain.repository.UserTierRepository
import rankifyHub.userTier.presentation.dto.CreateUserTierRequest
import java.util.UUID

@Service
class UserTierUseCase(
    private val userTierRepository: UserTierRepository,
    private val userTierFactory: UserTierFactory
) {

    @Transactional
    fun create(request: CreateUserTierRequest): UserTier {
        val anonymousId = AnonymousId(request.anonymousId)
        val categoryId = UUID.fromString(request.categoryId)
        val name = UserTierName(request.name)
        val isPublic = request.isPublic

        val levels = request.levels.map { levelRequest ->
            val levelName = UserTierName(levelRequest.name)
            val items = levelRequest.items.map { itemRequest ->
                UserTierItemData(
                    itemId = UUID.fromString(itemRequest.itemId),
                    orderIndex = OrderIndex(itemRequest.orderIndex)
                )
            }
            UserTierLevelData(
                name = levelName,
                orderIndex = OrderIndex(levelRequest.orderIndex),
                items = items
            )
        }

        val userTier = userTierFactory.create(
            anonymousId = anonymousId,
            categoryId = categoryId,
            name = name,
            isPublic = isPublic,
            levels = levels
        )
        return userTierRepository.save(userTier)
    }
}
