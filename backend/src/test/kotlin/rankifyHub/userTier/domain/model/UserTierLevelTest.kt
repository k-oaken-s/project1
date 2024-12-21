package rankifyHub.userTier.domain.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import java.time.Instant
import java.util.*
import rankifyHub.userTier.domain.vo.AccessUrl
import rankifyHub.userTier.domain.vo.AnonymousId
import rankifyHub.userTier.domain.vo.OrderIndex
import rankifyHub.userTier.domain.vo.UserTierName

class UserTierLevelTest :
  StringSpec({
    // UserTierLevelにアイテムを追加するテスト
    "アイテムを追加できることを確認" {
      val userTierLevel =
        UserTierLevel(
          id = UUID.randomUUID(),
          name = "Test Level",
          userTier = mockk(relaxed = true),
          orderIndex = OrderIndex(1),
          createdAt = Instant.now(),
          updatedAt = Instant.now(),
          _items = mutableListOf() // 空のリストを初期化
        )
      val userTier =
        UserTier(
          anonymousId = AnonymousId("test"),
          categoryId = UUID.randomUUID(),
          name = UserTierName("Test UserTier"),
          isPublic = true,
          accessUrl = AccessUrl("test-url")
        )

      val item =
        UserTierLevelItem(
          userTierLevel = userTierLevel,
          userTier = userTier,
          itemId = UUID.randomUUID(),
          orderIndex = OrderIndex(1),
          createdAt = Instant.now(),
          updatedAt = Instant.now()
        )

      userTierLevel.addItem(item)

      userTierLevel.items shouldHaveSize 1 // アイテムが1件追加されていること
      userTierLevel.items shouldContain item // アイテムがリストに含まれていること
      item.orderIndex.value shouldBe 1 // アイテムの順序が正しいこと
      item.userTierLevel shouldBe userTierLevel // 親のUserTierLevelが正しく設定されていること
    }

    // UserTierLevelからアイテムを削除するテスト
    "アイテムを削除できることを確認" {
      val userTierLevel =
        UserTierLevel(
          id = UUID.randomUUID(),
          name = "Test Level",
          userTier = mockk(relaxed = true),
          orderIndex = OrderIndex(1),
          createdAt = Instant.now(),
          updatedAt = Instant.now(),
          _items = mutableListOf() // 空のリストを初期化
        )
      val item =
        UserTierLevelItem(
          userTierLevel = userTierLevel,
          userTier = mockk(relaxed = true),
          itemId = UUID.randomUUID(),
          orderIndex = OrderIndex(1),
          createdAt = Instant.now(),
          updatedAt = Instant.now()
        )

      userTierLevel.addItem(item)
      userTierLevel.removeItem(item)

      userTierLevel.items shouldHaveSize 0 // アイテムが削除されていること
    }

    // UserTierLevel内のアイテムを並べ替えるテスト
    "アイテムの順序を並べ替えられることを確認" {
      val userTierLevel =
        UserTierLevel(
          id = UUID.randomUUID(),
          name = "Test Level",
          userTier = mockk(relaxed = true),
          orderIndex = OrderIndex(1),
          createdAt = Instant.now(),
          updatedAt = Instant.now(),
          _items = mutableListOf() // 空のリストを初期化
        )

      val item1 =
        UserTierLevelItem(
          userTierLevel = userTierLevel,
          userTier = mockk(relaxed = true),
          itemId = UUID.randomUUID(),
          orderIndex = OrderIndex(1),
          createdAt = Instant.now(),
          updatedAt = Instant.now()
        )
      val item2 =
        UserTierLevelItem(
          userTierLevel = userTierLevel,
          userTier = mockk(relaxed = true),
          itemId = UUID.randomUUID(),
          orderIndex = OrderIndex(2),
          createdAt = Instant.now(),
          updatedAt = Instant.now()
        )

      userTierLevel.addItem(item1)
      userTierLevel.addItem(item2)

      userTierLevel.items[0].orderIndex.value shouldBe 1 // 最初のアイテムの順序が1
      userTierLevel.items[1].orderIndex.value shouldBe 2 // 2番目のアイテムの順序が2

      userTierLevel.removeItem(item1)

      userTierLevel.items[0].orderIndex.value shouldBe 1 // 残ったアイテムの順序が更新されること
    }
  })
