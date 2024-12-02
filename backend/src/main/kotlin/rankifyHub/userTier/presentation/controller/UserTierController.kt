package rankifyHub.userTier.presentation.controller

import org.flywaydb.core.extensibility.Tier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.context.request.async.DeferredResult
import rankifyHub.userTier.application.CreateUserTierUseCase
import rankifyHub.userTier.presentation.dto.CreateUserTierRequest
import rankifyHub.userTier.presentation.dto.UserTierResponse
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.ForkJoinPool

class UserTierController(
    private val createUserTierUseCase: CreateUserTierUseCase
) {

    @PostMapping
    fun create(@RequestBody request: CreateUserTierRequest): ResponseEntity<UserTierResponse> {
        val userTier = createUserTierUseCase.create(request)
        return ResponseEntity.ok(UserTierResponse.fromEntity(userTier))
    }

    @GetMapping
    fun getLatestTiers(
        @RequestParam(required = false, defaultValue = "10") limit: Int,
        @RequestParam(required = false, defaultValue = "createdAt_desc") sort: String
    ): List<Tier> {
        val tiers = when (sort) {
            "createdAt_desc" -> tierRepository.findAllByOrderByCreatedAtDesc()
            "createdAt_asc" -> tierRepository.findAllByOrderByCreatedAtAsc()
            else -> tierRepository.findAll()
        }
        return tiers.take(limit)
    }

    @GetMapping("/long-polling")
    fun longPollingTiers(@RequestParam since: Long): DeferredResult<List<Tier>> {
        val deferredResult = DeferredResult<List<Tier>>(30000L) // タイムアウト30秒

        ForkJoinPool.commonPool().submit {
            val timestamp =
                LocalDateTime.ofEpochSecond(since / 1000, ((since % 1000) * 1_000_000).toInt(), ZoneOffset.UTC)
            while (!deferredResult.isSetOrExpired) {
                val newTiers = tierRepository.findAllByCreatedAtAfterOrderByCreatedAtDesc(timestamp)
                if (newTiers.isNotEmpty()) {
                    deferredResult.setResult(newTiers)
                    break
                }
                Thread.sleep(1000) // 1秒待機
            }
        }

        deferredResult.onTimeout {
            deferredResult.setResult(emptyList())
        }

        return deferredResult
    }
}
