package rankifyHub.userTier.presentation.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rankifyHub.userTier.application.usecase.UserTierUseCase
import rankifyHub.userTier.presentation.dto.CreateUserTierRequest
import rankifyHub.userTier.presentation.dto.UserTierResponse

@RestController
@RequestMapping("/api/user-tiers")
class UserTierController(private val userTierUseCase: UserTierUseCase) {

    @PostMapping
    fun createUserTier(@RequestBody request: CreateUserTierRequest): ResponseEntity<UserTierResponse> {
        val userTier = userTierUseCase.createUserTier(request)
        return ResponseEntity.ok(UserTierResponse.fromEntity(userTier))
    }
}
