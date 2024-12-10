package rankifyHub.userTier.domain.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import rankifyHub.userTier.domain.vo.OrderIndex

class UserTierLevelItemTest : StringSpec({
    // UserTierLevelItemの順序を更新するテスト
    "順序を更新できることを確認" {
        val item = UserTierLevelItem(itemId = "item1")

        item.updateOrder(OrderIndex(2))

        item.orderIndex.value shouldBe 2 // 順序が2に更新されていること
    }
})
