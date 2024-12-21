package rankifyHub.category.domain.model

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class ItemTest :
  DescribeSpec({
    describe("Item エンティティ") {

      // アイテム作成メソッドのテスト
      context("create メソッド") {
        it("名前と画像データを指定して新しいアイテムを作成できること") {
          // 準備
          val name = "サンプルアイテム"
          val image = ByteArray(10) { it.toByte() } // サンプルバイナリデータ
          val category = Category.create("カテゴリー名", null, null)

          // 実行
          val item = Item.create(name, image, category)

          // 検証
          item.shouldBeInstanceOf<Item>()
          item.name shouldBe name
          item.image shouldBe image
          item.category shouldBe category
          item.id shouldNotBe null // IDが生成されていること
        }

        it("画像がnullでも新しいアイテムを作成できること") {
          // 準備
          val name = "画像なしアイテム"
          val category = Category.create("カテゴリー名", null, null)

          // 実行
          val item = Item.create(name, null, category)

          // 検証
          item.name shouldBe name
          item.image shouldBe null
          item.category shouldBe category
        }
      }

      // アイテム更新メソッドのテスト
      context("update メソッド") {
        it("アイテムの名前と画像を更新できること") {
          // 準備
          val originalName = "元のアイテム"
          val originalImage = ByteArray(5) { it.toByte() }
          val category = Category.create("カテゴリー名", null, null)
          val item = Item.create(originalName, originalImage, category)

          val updatedName = "更新後のアイテム"
          val updatedImage = ByteArray(10) { it.toByte() }

          // 実行
          val updatedItem = item.update(updatedName, updatedImage, "更新後の説明")

          // 検証
          updatedItem.id shouldBe item.id // IDは変更されないこと
          updatedItem.name shouldBe updatedName
          updatedItem.image shouldBe updatedImage
          updatedItem.description shouldBe "更新後の説明"
        }

        it("画像をnullに更新できること") {
          // 準備
          val originalName = "元のアイテム"
          val originalImage = ByteArray(5) { it.toByte() }
          val category = Category.create("カテゴリー名", null, null)
          val item = Item.create(originalName, originalImage, category)

          // 実行
          val updatedItem = item.update(originalName, null, "説明を変更")

          // 検証
          updatedItem.name shouldBe originalName
          updatedItem.image shouldBe null // 画像が削除されていること
          updatedItem.description shouldBe "説明を変更"
        }
      }

      // デフォルト値のテスト
      context("デフォルト値") {
        it("IDと名前のデフォルト値が設定されていること") {
          // 実行
          val item = Item.create("", null, Category())

          // 検証
          item.id shouldNotBe null // デフォルトのIDが生成されていること
          item.name shouldBe "" // 名前のデフォルト値が空文字列であること
        }
      }
    }
  })
