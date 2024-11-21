package rankifyHub.userTier.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rankifyHub.userTier.domain.model.*
import rankifyHub.userTier.domain.repository.UserTierRepository
import rankifyHub.userTier.domain.vo.AnonymousId
import rankifyHub.userTier.domain.vo.OrderIndex
import rankifyHub.userTier.domain.vo.UserTierName
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

        // レベルとアイテムのマッピングを生成
        val levels = request.levels.map { levelRequest ->
            val levelName = UserTierName(levelRequest.name)
            val levelOrderIndex = OrderIndex(levelRequest.orderIndex)

            // アイテムのリストを生成
            val items = levelRequest.items.map { itemRequest ->
                UserTierItemData(
                    itemId = UUID.fromString(itemRequest.itemId),
                    orderIndex = OrderIndex(itemRequest.orderIndex)
                )
            }

            // レベルデータを作成
            UserTierLevelData(
                name = levelName,
                orderIndex = levelOrderIndex,
                items = items
            )
        }

        // UserTierを作成
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
