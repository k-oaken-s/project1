package rankifyHub.userTier.application

import rankifyHub.userTier.domain.repository.UserTierRepository
import rankifyHub.userTier.presentation.dto.UserTierResponse

class GetLatestUserTiersUseCase(private val userTierRepository: UserTierRepository) {

    fun execute(): List<UserTierResponse> {
        val userTiers = userTierRepository.findByIsPublicTrueOrderByCreatedAtDesc()
        return userTiers.map { UserTierResponse.fromEntity(it) }
    }
}
