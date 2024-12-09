package rankifyHub.userTier.presentation.controller

import java.time.Instant
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.async.DeferredResult
import rankifyHub.userTier.application.CreateUserTierUseCase
import rankifyHub.userTier.application.GetLatestUserTiersUseCase
import rankifyHub.userTier.presentation.dto.CreateUserTierRequest
import rankifyHub.userTier.presentation.dto.UserTierDetailResponse
import rankifyHub.userTier.presentation.dto.UserTierResponse
import rankifyHub.userTier.presentation.presenter.UserTierPresenter

@RestController
@RequestMapping("/user-tiers")
class UserTierController(
  private val createUserTierUseCase: CreateUserTierUseCase,
  private val getLatestUserTiersUseCase: GetLatestUserTiersUseCase,
  private val presenter: UserTierPresenter
) {

  @PostMapping
  fun create(@RequestBody request: CreateUserTierRequest): ResponseEntity<UserTierDetailResponse> {
    val userTier = createUserTierUseCase.create(request)
    return ResponseEntity.ok(UserTierDetailResponse.fromEntity(userTier))
  }

  /**
   * 公開されているUserTierを取得します。
   *
   * @return 公開されているUserTierのリスト
   */
  @GetMapping("/public")
  fun getPublicUserTiers(): List<UserTierResponse> {
    val userTiersWithCategory = getLatestUserTiersUseCase.getPublicUserTiers()
    return userTiersWithCategory.map { presenter.toResponse(it) }
  }

  /**
   * 指定された件数の最新のUserTierを取得します。
   *
   * @param limit 取得する件数
   * @return 最新のUserTierリスト
   */
  @GetMapping("/latest")
  fun getLatestUserTiers(@RequestParam limit: Int): List<UserTierResponse> {
    val userTiersWithCategory = getLatestUserTiersUseCase.getLatestUserTiers(limit)
    return userTiersWithCategory.map { presenter.toResponse(it) }
  }

  /**
   * 指定された時刻以降に作成されたUserTierをロングポーリングで取得します。
   *
   * @param since 開始時刻（ミリ秒）
   * @return 指定時刻以降のUserTierリスト
   */
  @GetMapping("/since")
  fun getUserTiersSince(@RequestParam since: Long): DeferredResult<List<UserTierResponse>> {
    val deferredResult = DeferredResult<List<UserTierResponse>>(30000L) // タイムアウト30秒
    val executor = Executors.newSingleThreadExecutor()

    CompletableFuture.runAsync(
        {
          val timestamp = Instant.ofEpochMilli(since)
          while (!deferredResult.isSetOrExpired) {
            val newUserTiers = getLatestUserTiersUseCase.getUserTiersSince(timestamp)
            if (newUserTiers.isNotEmpty()) {
              val responses = newUserTiers.map { presenter.toResponse(it) }
              deferredResult.setResult(responses)
              break
            }
            Thread.sleep(1000)
          }
        },
        executor
      )
      .exceptionally { ex ->
        deferredResult.setErrorResult(ex)
        null
      }

    deferredResult.onTimeout { deferredResult.setResult(emptyList()) }

    return deferredResult
  }
}
