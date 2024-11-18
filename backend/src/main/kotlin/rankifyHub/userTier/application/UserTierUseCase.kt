package rankifyHub.userTier.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rankifyHub.userTier.domain.model.UserTier
import rankifyHub.userTier.domain.model.UserTierItem
import rankifyHub.userTier.domain.model.UserTierConfig
import rankifyHub.userTier.domain.repository.UserTierRepository
import rankifyHub.userTier.presentation.dto.CreateUserTierRequest

@Service
class UserTierUseCase(private val userTierRepository: UserTierRepository) {

    @Transactional
    fun createUserTier(request: CreateUserTierRequest): UserTier {
        // UserTierエンティティを作成
        val userTier = UserTier(
            anonymousId = request.anonymousId,
            categoryId = request.categoryId,
            tierName = request.tierName,
            order = request.order
        )

        // UserTierItemを追加
        val items = request.items.mapIndexed { index, itemRequest ->
            UserTierItem(
                userTier = userTier,
                itemId = itemRequest.itemId,
                order = index + 1
            )
        }

        // UserTierConfigを追加
        val configs = request.configs.mapIndexed { index, configRequest ->
            UserTierConfig(
                userTier = userTier,
                key = configRequest.key,
                value = configRequest.value,
                order = index + 1
            )
        }

        // エンティティに関連付け
        userTier.items = items
        userTier.configs = configs

        // 保存処理
        return userTierRepository.save(userTier)
    }
}
