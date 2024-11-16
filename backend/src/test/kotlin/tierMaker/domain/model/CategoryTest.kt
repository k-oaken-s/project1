package tierMaker.domain.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow
import java.util.*

class CategoryTest : StringSpec({

    "should create a category successfully" {
        // Arrange
        val name = "Test Category"
        val description = "This is a test category"
        val image = "image-data".toByteArray()

        // Act
        val category = Category.create(name, description, image)

        // Assert
        category.name shouldBe name
        category.description shouldBe description
        category.image shouldBe image
        category.items shouldHaveSize 0
    }

    "should add an item to a category" {
        // Arrange
        val category = Category.create("Test Category", null, null)

        // Act
        val item = category.addItem("Test Item", null)

        // Assert
        category.items shouldHaveSize 1
        category.items shouldContainExactly listOf(item)
        category.items[0].name shouldBe "Test Item"
    }

    "should update an existing item in a category" {
        // Arrange
        val category = Category.create("Test Category", null, null)
        val item = category.addItem("Old Item", "old-image".toByteArray())

        // Act
        val updatedItem = category.updateItem(
            itemId = item.id,
            name = "Updated Item",
            image = "updated-image".toByteArray(),
            keepCurrentImage = false
        )

        // Assert
        updatedItem.name shouldBe "Updated Item"
        updatedItem.image shouldBe "updated-image".toByteArray()
        category.items shouldHaveSize 1
        category.items[0] shouldBe updatedItem
    }

    "should throw an exception when updating a non-existent item" {
        // Arrange
        val category = Category.create("Test Category", null, null)

        // Act & Assert
        shouldThrow<IllegalArgumentException> {
            category.updateItem(
                itemId = UUID.randomUUID().toString(),
                name = "Updated Item",
                image = null,
                keepCurrentImage = true
            )
        }
    }

    "should keep the current image when updating an item with keepCurrentImage=true" {
        // Arrange
        val category = Category.create("Test Category", null, null)
        val item = category.addItem("Item Name", "initial-image".toByteArray())

        // Act
        val updatedItem = category.updateItem(
            itemId = item.id,
            name = "Updated Name",
            image = null, // 新しい画像を渡さない
            keepCurrentImage = true
        )

        // Assert
        updatedItem.name shouldBe "Updated Name"
        updatedItem.image shouldBe "initial-image".toByteArray() // 既存の画像を保持
    }
})
