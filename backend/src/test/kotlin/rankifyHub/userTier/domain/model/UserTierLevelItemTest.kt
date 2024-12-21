package rankifyHub.userTier.domain.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import java.time.Instant
import java.util.*
import rankifyHub.userTier.domain.vo.OrderIndex

class UserTierLevelItemTest :
  StringSpec({
    "順序を更新できることを確認" {
      // テスト用のデータを準備
      val userTierLevel = mockk<UserTierLevel>(relaxed = true)
      val userTier = mockk<UserTier>(relaxed = true)
      val itemId = UUID.randomUUID()
      val initialOrderIndex = OrderIndex(1)
      val createdAt = Instant.now()
      val updatedAt = Instant.now()

      // UserTierLevelItemを作成
      val item =
        UserTierLevelItem(
          userTierLevel = userTierLevel,
          userTier = userTier,
          itemId = itemId,
          orderIndex = initialOrderIndex,
          createdAt = createdAt,
          updatedAt = updatedAt
        )

      // 順序を更新
      item.updateOrder(OrderIndex(2))

      // 検証
      item.orderIndex.value shouldBe 2 // 順序が2に更新されていること
    }
  })
