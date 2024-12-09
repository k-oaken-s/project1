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

      // getAllCategoriesメソッドのテスト
      context("getAllCategories") {
        it("すべてのカテゴリを取得できること") {
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

      // getCategoryWithItemsメソッドのテスト
      context("getCategoryWithItems") {
        it("指定されたIDのカテゴリを取得できること") {
          val categoryId = UUID.randomUUID().toString()
          val category = Category.create("Category1", "Description1", null)
          every { categoryRepository.findById(categoryId) } returns Optional.of(category)

          val result = categoryUseCase.getCategoryWithItems(categoryId)

          result shouldBe category
          verify { categoryRepository.findById(categoryId) }
        }

        it("カテゴリが見つからない場合に例外をスローすること") {
          val categoryId = UUID.randomUUID().toString()
          every { categoryRepository.findById(categoryId) } returns Optional.empty()

          shouldThrow<IllegalArgumentException> { categoryUseCase.getCategoryWithItems(categoryId) }
            .message shouldBe "Category not found"

          verify { categoryRepository.findById(categoryId) }
        }
      }

      // addCategoryメソッドのテスト
      context("addCategory") {
        it("新しいカテゴリを追加できること") {
          val addCategoryDto = AddCategoryDto("New Category", "New Description")
          val category = Category.create(addCategoryDto.name, addCategoryDto.description, null)
          every { categoryRepository.save(any()) } returns category

          val result = categoryUseCase.addCategory(addCategoryDto, null)

          result.name shouldBe addCategoryDto.name
          result.description shouldBe addCategoryDto.description
          verify { categoryRepository.save(match { it.name == addCategoryDto.name }) }
        }
      }

      // deleteCategoryメソッドのテスト
      context("deleteCategory") {
        it("指定されたIDのカテゴリを削除できること") {
          val categoryId = UUID.randomUUID().toString()
          every { categoryRepository.deleteById(categoryId) } just Runs

          categoryUseCase.deleteCategory(categoryId)

          verify { categoryRepository.deleteById(categoryId) }
        }
      }

      // addItemToCategoryメソッドのテスト
      context("addItemToCategory") {
        it("カテゴリにアイテムを追加できること") {
          val categoryId = UUID.randomUUID().toString()
          val category = mockk<Category>()
          val item = Item.create("New Item", null, category, "")
          every { categoryRepository.findById(categoryId) } returns Optional.of(category)
          every { category.addItem("New Item", null, "") } returns item
          every { categoryRepository.save(category) } returns category

          val result =
            categoryUseCase.addItemToCategory(categoryId, """{"name":"New Item"}""", null)

          result.name shouldBe "New Item"
          verify { category.addItem("New Item", null, "") }
          verify { categoryRepository.save(category) }
        }

        it("カテゴリが見つからない場合に例外をスローすること") {
          val categoryId = UUID.randomUUID().toString()
          every { categoryRepository.findById(categoryId) } returns Optional.empty()

          shouldThrow<IllegalArgumentException> {
              categoryUseCase.addItemToCategory(categoryId, """{"name":"New Item"}""", null)
            }
            .message shouldBe "Category not found"

          verify { categoryRepository.findById(categoryId) }
        }
      }

      // updateItemInCategoryメソッドのテスト
      context("updateItemInCategory") {
        it("カテゴリ内のアイテムを更新できること") {
          val categoryId = UUID.randomUUID().toString()
          val itemId = UUID.randomUUID().toString()
          val category = mockk<Category>()
          val updatedItem = Item.create("Updated Item", null, category, "")
          every { categoryRepository.findById(categoryId) } returns Optional.of(category)
          every {
            category.updateItem(
              itemId = itemId,
              name = "Updated Item",
              image = null,
              keepCurrentImage = true,
              description = ""
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
          verify { category.updateItem(itemId, "Updated Item", null, true, "") }
          verify { categoryRepository.save(category) }
        }

        it("カテゴリが見つからない場合に例外をスローすること") {
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
