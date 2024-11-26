package rankifyHub.userTier.domain.vo

import jakarta.persistence.Embeddable
import java.util.UUID

/**
 * アクセスURL
 *
 * @property value アクセスURLの値
 */
@Embeddable
data class AccessUrl(val value: String) {
  init {
    require(value.isNotBlank()) { "AccessUrl cannot be blank" }
    require(value.length <= 255) { "AccessUrl must be 255 characters or less" }
    require(value.startsWith("http://") || value.startsWith("https://")) {
      "AccessUrl must start with http:// or https://"
    }
  }

  companion object {
    /** 新しいアクセスURLを生成 */
    fun generate(): AccessUrl {
      val uuid = UUID.randomUUID().toString()
      return AccessUrl("https://example.com/$uuid")
    }
  }
}
