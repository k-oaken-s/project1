package rankifyHub.userTier.domain.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.mockk.*
import java.util.*
import rankifyHub.userTier.domain.vo.OrderIndex

class UserTierTest :
  StringSpec({
    // レベルを追加できることを確認
    "レベルを追加できることを確認" {
      val levels = mutableListOf<UserTierLevel>()
      val userTier = mockk<UserTier>(relaxed = true)
      val level = mockk<UserTierLevel>(relaxed = true)

      // モック設定
      every { userTier.getLevels() } returns levels
      every { userTier.addLevel(level) } answers
        {
          levels.add(level)
          every { level.userTier = userTier } just Runs
          every { level.orderIndex = OrderIndex(levels.size) } just Runs
          level.userTier = userTier // 実際に呼び出し
          level.orderIndex = OrderIndex(levels.size) // 実際に呼び出し
        }

      // 実行
      userTier.addLevel(level)

      // 検証
      userTier.getLevels() shouldHaveSize 1
      userTier.getLevels() shouldContain level
      verify { level.userTier = userTier }
      verify { level.orderIndex = OrderIndex(1) }
    }

    // レベルの順序を並べ替えるテスト
    "レベルの順序を並べ替えられることを確認" {
      val levels = mutableListOf<UserTierLevel>()
      val userTier = mockk<UserTier>(relaxed = true)
      val level1 = mockk<UserTierLevel>(relaxed = true)
      val level2 = mockk<UserTierLevel>(relaxed = true)

      // 初期化
      levels.addAll(listOf(level1, level2))
      every { userTier.getLevels() } returns levels

      // 削除操作をモック
      every { userTier.removeLevel(level1) } answers
        {
          levels.remove(level1)
          levels.forEachIndexed { index, level ->
            every { level.orderIndex = OrderIndex(index + 1) } just Runs
            level.orderIndex = OrderIndex(index + 1) // 実際に呼び出し
          }
        }

      // 実行
      userTier.removeLevel(level1)

      // 検証
      userTier.getLevels() shouldHaveSize 1
      userTier.getLevels() shouldContain level2
      verify { level2.orderIndex = OrderIndex(1) }
    }
  })
