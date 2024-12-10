package rankifyHub.userTier.domain.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class UserTierLevelTest : StringSpec({
    // UserTierLevelにアイテムを追加するテスト
    "アイテムを追加できることを確認" {
        val userTierLevel = UserTierLevel(name = "Level 1")
        val item = UserTierLevelItem(itemId = "item1")

        userTierLevel.addItem(item)

        userTierLevel.items shouldHaveSize 1 // アイテムが1件追加されていること
        userTierLevel.items shouldContain item // アイテムがリストに含まれていること
        item.orderIndex.value shouldBe 1 // アイテムの順序が正しいこと
        item.userTierLevel shouldBe userTierLevel // 親のUserTierLevelが正しく設定されていること
    }

    // UserTierLevelからアイテムを削除するテスト
    "アイテムを削除できることを確認" {
        val userTierLevel = UserTierLevel(name = "Level 1")
        val item = UserTierLevelItem(itemId = "item1")
        userTierLevel.addItem(item)

        userTierLevel.removeItem(item)

        userTierLevel.items shouldHaveSize 0 // アイテムが削除されていること
    }

    // UserTierLevel内のアイテムを並べ替えるテスト
    "アイテムの順序を並べ替えられることを確認" {
        val userTierLevel = UserTierLevel(name = "Level 1")
        val item1 = UserTierLevelItem(itemId = "item1")
        val item2 = UserTierLevelItem(itemId = "item2")

        userTierLevel.addItem(item1)
        userTierLevel.addItem(item2)

        userTierLevel.items[0].orderIndex.value shouldBe 1 // 最初のアイテムの順序が1
        userTierLevel.items[1].orderIndex.value shouldBe 2 // 2番目のアイテムの順序が2

        userTierLevel.removeItem(item1)

        userTierLevel.items[0].orderIndex.value shouldBe 1 // 残ったアイテムの順序が更新されること
    }
})
