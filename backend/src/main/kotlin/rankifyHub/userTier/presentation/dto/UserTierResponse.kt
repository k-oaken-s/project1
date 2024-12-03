package rankifyHub.userTier.presentation.dto

import java.time.Instant

/** クライアントに返却するUserTierのレスポンスDTO */
data class UserTierResponse(
  val accessUrl: String,
  val createdAt: Instant,
  val name: String,
  val categoryName: String,
  val categoryImageUrl: String // 画像のURLを保持
)
