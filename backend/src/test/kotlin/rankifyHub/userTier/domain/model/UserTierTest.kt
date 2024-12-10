package rankifyHub.userTier.domain.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import rankifyHub.userTier.domain.vo.AccessUrl
import rankifyHub.userTier.domain.vo.AnonymousId
import rankifyHub.userTier.domain.vo.UserTierName

class UserTierTest : StringSpec({
    // UserTierにレベルを追加するテスト
    "レベルを追加できることを確認" {
        val userTier = UserTier(
            anonymousId = AnonymousId("anonymous1"),
            categoryId = "category1",
            name = UserTierName("Test Tier"),
            isPublic = true,
            accessUrl = AccessUrl("test-url")
        )
        val level = UserTierLevel(name = "Level 1")

        userTier.addLevel(level)

        userTier.getLevels() shouldHaveSize 1 // レベルが1件追加されていることを確認
        userTier.getLevels() shouldContain level // レベルがリストに含まれていることを確認
        level.orderIndex.value shouldBe 1 // レベルの順序が正しいことを確認
        level.userTier shouldBe userTier // 親のUserTierが正しく設定されていることを確認
    }

    // UserTierからレベルを削除するテスト
    "レベルを削除できることを確認" {
        val userTier = UserTier(
            anonymousId = AnonymousId("anonymous1"),
            categoryId = "category1",
            name = UserTierName("Test Tier"),
            isPublic = true,
            accessUrl = AccessUrl("test-url")
        )
        val level = UserTierLevel(name = "Level 1")
        userTier.addLevel(level)

        userTier.removeLevel(level)

        userTier.getLevels() shouldHaveSize 0 // レベルが削除されていることを確認
    }

    // UserTier内のレベルを並べ替えるテスト
    "レベルの順序を並べ替えられることを確認" {
        val userTier = UserTier(
            anonymousId = AnonymousId("anonymous1"),
            categoryId = "category1",
            name = UserTierName("Test Tier"),
            isPublic = true,
            accessUrl = AccessUrl("test-url")
        )
        val level1 = UserTierLevel(name = "Level 1")
        val level2 = UserTierLevel(name = "Level 2")

        userTier.addLevel(level1)
        userTier.addLevel(level2)

        userTier.getLevels()[0].orderIndex.value shouldBe 1 // 最初のレベルの順序が1であること
        userTier.getLevels()[1].orderIndex.value shouldBe 2 // 2番目のレベルの順序が2であること

        userTier.removeLevel(level1)

        userTier.getLevels()[0].orderIndex.value shouldBe 1 // 残ったレベルの順序が正しく更新されること
    }
})
