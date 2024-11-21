package rankifyHub.category.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import java.util.*
import rankifyHub.category.domain.model.Category
import rankifyHub.category.domain.model.Item
import rankifyHub.category.domain.repository.CategoryRepository

class CategoryUseCaseTest :
  DescribeSpec({
    val categoryRepository: CategoryRepository = mockk()
    val categoryUseCase = CategoryUseCase(categoryRepository)

    describe("CategoryUseCase") {
      context("getAllCategories") {
        it("should return all categories") {
          val categories =
            listOf(
              Category.create("Category1", "Description1", null),
              Category.create("Category2", "Description2", null)
            )
          every { categoryRepository.findAll() } returns categories

          val result = categoryUseCase.getAllCategories()

          result shouldBe categories
          verify { categoryRepository.findAll() }
        }
      }

      context("getCategoryWithItems") {
        it("should return the category with the given ID") {
          val categoryId = UUID.randomUUID().toString()
          val category = Category.create("Category1", "Description1", null)
          every { categoryRepository.findById(categoryId) } returns Optional.of(category)

          val result = categoryUseCase.getCategoryWithItems(categoryId)

          result shouldBe category
          verify { categoryRepository.findById(categoryId) }
        }

        it("should throw IllegalArgumentException if category not found") {
          val categoryId = UUID.randomUUID().toString()
          every { categoryRepository.findById(categoryId) } returns Optional.empty()

          shouldThrow<IllegalArgumentException> { categoryUseCase.getCategoryWithItems(categoryId) }
            .message shouldBe "Category not found"

          verify { categoryRepository.findById(categoryId) }
        }
      }

      context("addCategory") {
        it("should add a new category") {
          val addCategoryDto = AddCategoryDto("New Category", "New Description")
          val category = Category.create(addCategoryDto.name, addCategoryDto.description, null)
          every { categoryRepository.save(any()) } returns category

          val result = categoryUseCase.addCategory(addCategoryDto, null)

          result.name shouldBe addCategoryDto.name
          result.description shouldBe addCategoryDto.description
          verify { categoryRepository.save(any()) }
        }
      }

      context("deleteCategory") {
        it("should delete a category by ID") {
          val categoryId = UUID.randomUUID().toString()
          every { categoryRepository.deleteById(categoryId) } just Runs

          categoryUseCase.deleteCategory(categoryId)

          verify { categoryRepository.deleteById(categoryId) }
        }
      }

      context("addItemToCategory") {
        it("should add an item to a category") {
          val categoryId = UUID.randomUUID().toString()
          val category = mockk<Category>(relaxed = true)
          val item = Item.create("New Item", null)
          every { categoryRepository.findById(categoryId) } returns Optional.of(category)
          every { category.addItem("New Item", null) } returns item
          every { categoryRepository.save(category) } returns category

          val result =
            categoryUseCase.addItemToCategory(categoryId, """{"name":"New Item"}""", null)

          result.name shouldBe "New Item"
          verify { category.addItem("New Item", null) }
          verify { categoryRepository.save(category) }
        }

        it("should throw IllegalArgumentException if category not found") {
          val categoryId = UUID.randomUUID().toString()
          every { categoryRepository.findById(categoryId) } returns Optional.empty()

          shouldThrow<IllegalArgumentException> {
              categoryUseCase.addItemToCategory(categoryId, """{"name":"New Item"}""", null)
            }
            .message shouldBe "Category not found"

          verify { categoryRepository.findById(categoryId) }
        }
      }

      context("updateItemInCategory") {
        it("should update an item in a category") {
          val categoryId = UUID.randomUUID().toString()
          val itemId = UUID.randomUUID().toString()
          val category = mockk<Category>(relaxed = true)
          val updatedItem = Item.create("Updated Item", null)
          every { categoryRepository.findById(categoryId) } returns Optional.of(category)
          every {
            category.updateItem(
              itemId = itemId,
              name = "Updated Item",
              image = null,
              keepCurrentImage = true
            )
          } returns updatedItem
          every { categoryRepository.save(category) } returns category

          val result =
            categoryUseCase.updateItemInCategory(
              categoryId,
              itemId,
              """{"name":"Updated Item"}""",
              null,
              true
            )

          result.name shouldBe "Updated Item"
          verify { category.updateItem(itemId, "Updated Item", null, true) }
          verify { categoryRepository.save(category) }
        }

        it("should throw IllegalArgumentException if category not found") {
          val categoryId = UUID.randomUUID().toString()
          val itemId = UUID.randomUUID().toString()
          every { categoryRepository.findById(categoryId) } returns Optional.empty()

          shouldThrow<IllegalArgumentException> {
              categoryUseCase.updateItemInCategory(
                categoryId,
                itemId,
                """{"name":"Updated Item"}""",
                null,
                true
              )
            }
            .message shouldBe "Category not found"

          verify { categoryRepository.findById(categoryId) }
        }
      }
    }
  })
