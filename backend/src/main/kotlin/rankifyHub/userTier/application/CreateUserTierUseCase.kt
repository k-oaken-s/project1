package rankifyHub.userTier.application

import java.util.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rankifyHub.userTier.domain.model.UserTier
import rankifyHub.userTier.domain.model.UserTierFactory
import rankifyHub.userTier.domain.model.UserTierItemData
import rankifyHub.userTier.domain.model.UserTierLevelData
import rankifyHub.userTier.domain.repository.UserTierRepository
import rankifyHub.userTier.domain.vo.AnonymousId
import rankifyHub.userTier.domain.vo.OrderIndex
import rankifyHub.userTier.domain.vo.UserTierName
import rankifyHub.userTier.presentation.dto.CreateUserTierRequest

@Service
class CreateUserTierUseCase(
  private val userTierRepository: UserTierRepository,
  private val userTierFactory: UserTierFactory
) {

  @Transactional
  fun create(request: CreateUserTierRequest): UserTier {
    val anonymousId = AnonymousId(request.anonymousId)
    val categoryId = UUID.fromString(request.categoryId)
    //    val categoryId = String(request.categoryId.toByteArray(Charsets.UTF_8), Charsets.UTF_8)
    val name = UserTierName(request.name)
    val isPublic = request.isPublic

    val levels =
      request.levels.map { levelRequest ->
        val levelName = UserTierName(levelRequest.name)
        val levelOrderIndex = OrderIndex(levelRequest.orderIndex)

        val items =
          levelRequest.items.map { itemRequest ->
            UserTierItemData(
              itemId = UUID.fromString(itemRequest.itemId),
              orderIndex = OrderIndex(itemRequest.orderIndex)
            )
          }

        UserTierLevelData(name = levelName, orderIndex = levelOrderIndex, items = items)
      }

    val userTier =
      userTierFactory.create(
        anonymousId = anonymousId,
        categoryId = categoryId,
        name = name,
        isPublic = isPublic,
        levels = levels
      )

    return userTierRepository.save(userTier)
  }
}
