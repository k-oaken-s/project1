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
    fun createUserTier(request: CreateUserTierRequest): UserTier {
        val anonymousId = AnonymousId(request.anonymousId)
        val categoryId = UUID.fromString(request.categoryId)
        val name = UserTierName(request.name)
        val isPublic = request.isPublic
        val levels = request.levels.map { levelRequest ->
            val levelName = UserTierName(levelRequest.name)
            val itemIds = levelRequest.items.map { UUID.fromString(it.itemId) }
            levelName to itemIds
        }

        val userTier = userTierFactory.create(
            anonymousId = anonymousId,
            categoryId = categoryId,
            name = name,
            isPublic = isPublic,
            levels = levels
        )

        // 保存処理
        return userTierRepository.save(userTier)
    }
}
