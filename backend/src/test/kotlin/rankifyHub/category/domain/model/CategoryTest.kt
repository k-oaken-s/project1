package rankifyHub.category.domain.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.util.*

class CategoryTest :
  StringSpec({

    // カテゴリーの作成テスト
    "カテゴリーが正しく作成されること" {
      // 準備
      val name = "テストカテゴリー"
      val description = "これはテストカテゴリーです"
      val image = "画像データ".toByteArray()

      // 実行
      val category = Category.create(name, description, image)

      // 検証
      category.name shouldBe name
      category.description shouldBe description
      category.image shouldBe image
      category.items shouldHaveSize 0 // 初期状態ではアイテムが空であること
    }

    // カテゴリーへのアイテム追加テスト
    "カテゴリーにアイテムを追加できること" {
      // 準備
      val category = Category.create("テストカテゴリー", null, null)

      // 実行
      val item = category.addItem("テストアイテム", null, null)

      // 検証
      category.items shouldHaveSize 1
      category.items shouldContainExactly listOf(item) // 正しいアイテムが追加されていること
      category.items[0].name shouldBe "テストアイテム"
    }

    // カテゴリー内のアイテム更新テスト
    "カテゴリー内のアイテムを更新できること" {
      // 準備
      val category = Category.create("テストカテゴリー", null, null)
      val item = category.addItem("古いアイテム", "古い画像".toByteArray(), "古い説明")

      // 実行
      val updatedItem =
        category.updateItem(
          itemId = item.id,
          name = "更新済みアイテム",
          image = "新しい画像".toByteArray(),
          keepCurrentImage = false,
          description = "新しい説明"
        )

      // 検証
      updatedItem.name shouldBe "更新済みアイテム"
      updatedItem.image shouldBe "新しい画像".toByteArray()
      updatedItem.description shouldBe "新しい説明"
      category.items shouldHaveSize 1 // アイテム数は1のままであること
      category.items[0] shouldBe updatedItem // 更新されたアイテムが正しく反映されていること
    }

    // 存在しないアイテムの更新時に例外がスローされるテスト
    "存在しないアイテムを更新しようとすると例外がスローされること" {
      // 準備
      val category = Category.create("テストカテゴリー", null, null)

      // 実行・検証
      shouldThrow<IllegalArgumentException> {
          category.updateItem(
            itemId = UUID.randomUUID(), // 存在しないID
            name = "更新済みアイテム",
            image = null,
            keepCurrentImage = true,
            description = null
          )
        }
        .message shouldBe "Item not found"
    }

    // アイテム更新時に既存の画像を保持するテスト
    "アイテム更新時にkeepCurrentImage=trueの場合、既存の画像が保持されること" {
      // 準備
      val category = Category.create("テストカテゴリー", null, null)
      val item = category.addItem("アイテム名", "初期画像".toByteArray(), "説明")

      // 実行
      val updatedItem =
        category.updateItem(
          itemId = item.id,
          name = "更新済みアイテム",
          image = null, // 新しい画像を指定しない
          keepCurrentImage = true,
          description = "更新済み説明"
        )

      // 検証
      updatedItem.name shouldBe "更新済みアイテム"
      updatedItem.image shouldBe "初期画像".toByteArray() // 既存の画像が保持されていること
      updatedItem.description shouldBe "更新済み説明"
    }
  })
