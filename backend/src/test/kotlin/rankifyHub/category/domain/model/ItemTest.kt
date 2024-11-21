package rankifyHub.category.domain.model

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.kotest.matchers.types.shouldBeInstanceOf
import java.util.*

class ItemTest :
  DescribeSpec({
    describe("Item entity") {
      context("create method") {
        it("should create a new Item with the given name and image") {
          val name = "Sample Item"
          val image = ByteArray(10) { it.toByte() } // サンプルバイナリデータ

          val item = Item.create(name, image)

          item.shouldBeInstanceOf<Item>()
          item.name shouldBe name
          item.image shouldBe image
          item.id.shouldNotBeBlank()
        }

        it("should allow creating an Item with a null image") {
          val name = "Item without Image"

          val item = Item.create(name, null)

          item.name shouldBe name
          item.image shouldBe null
        }
      }

      context("update method") {
        it("should update the name and image of an Item") {
          val originalName = "Original Item"
          val originalImage = ByteArray(5) { it.toByte() }
          val item = Item.create(originalName, originalImage)

          val updatedName = "Updated Item"
          val updatedImage = ByteArray(10) { it.toByte() }
          val updatedItem = item.update(updatedName, updatedImage)

          updatedItem.id shouldBe item.id // IDは変更されない
          updatedItem.name shouldBe updatedName
          updatedItem.image shouldBe updatedImage
        }

        it("should allow updating the image to null") {
          val originalName = "Original Item"
          val originalImage = ByteArray(5) { it.toByte() }
          val item = Item.create(originalName, originalImage)

          val updatedItem = item.update(originalName, null)

          updatedItem.name shouldBe originalName
          updatedItem.image shouldBe null
        }
      }

      context("toString method") {
        it("should return a string representation of the Item excluding the image") {
          val name = "Sample Item"
          val item = Item.create(name, null)

          item.toString() shouldBe "Item(id='${item.id}', name='$name')"
        }
      }

      context("default values") {
        it("should have a default ID and name if not provided") {
          val item = Item()

          item.id.shouldNotBeBlank()
          item.name shouldBe ""
        }
      }
    }
  })
