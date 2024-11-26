package rankifyHub.userTier.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
data class OrderIndex(val value: Int) {
  init {
    require(value > 0) { "Order Index must be greater than 0." }
  }
}
