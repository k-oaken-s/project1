package rankifyHub.category.application

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import java.util.*
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import rankifyHub.category.domain.model.Category
import rankifyHub.category.domain.model.Item
import rankifyHub.category.domain.repository.CategoryRepository

/**
 * カテゴリーに関するユースケースを提供するクラス
 *
 * カテゴリーの取得、追加、削除、およびアイテムの操作に関するビジネスロジックを定義します。
 *
 * @property categoryRepository カテゴリーの永続化を行うリポジトリ
 */
@Service
class CategoryUseCase(private val categoryRepository: CategoryRepository) {

  /**
   * すべてのカテゴリーを取得します。
   *
   * @return 登録されているカテゴリーのリスト
   */
  fun getAllCategories(): List<Category> = categoryRepository.findAll()

  /**
   * 指定されたIDのカテゴリーとそのアイテムを取得します。
   *
   * @param id カテゴリーのID
   * @return 指定されたIDのカテゴリー
   * @throws IllegalArgumentException カテゴリーが見つからない場合
   */
  fun getCategoryWithItems(id: UUID): Category =
    categoryRepository.findById(id).orElseThrow { IllegalArgumentException("Category not found") }

  /**
   * 新しいカテゴリーを追加します。
   *
   * @param addCategoryDto カテゴリーの追加情報を含むDTO
   * @param imageBytes カテゴリーの画像データ
   * @return 追加されたカテゴリー
   */
  fun addCategory(addCategoryDto: AddCategoryDto, imageBytes: ByteArray?): Category {
    val category =
      Category.create(
        name = addCategoryDto.name,
        description = addCategoryDto.description,
        image = imageBytes
      )
    return categoryRepository.save(category)
  }

  /**
   * 指定されたIDのカテゴリーを削除します。
   *
   * @param id 削除するカテゴリーのID
   */
  fun deleteCategory(id: UUID) = categoryRepository.deleteById(id)

  /**
   * カテゴリーにアイテムを追加します。
   *
   * @param categoryId カテゴリーのID
   * @param itemJson アイテムの情報を含むJSON文字列
   * @param imageBytes アイテムの画像データ
   * @return 追加されたアイテム
   * @throws IllegalArgumentException カテゴリーが見つからない場合
   */
  @Transactional
  fun addItemToCategory(categoryId: UUID, itemJson: String, imageBytes: ByteArray?): Item {
    val category =
      categoryRepository.findById(categoryId).orElseThrow {
        IllegalArgumentException("Category not found")
      }
    val itemName = ObjectMapper().readTree(itemJson).get("name").asText()
    val newItem = category.addItem(name = itemName, image = imageBytes, "")
    categoryRepository.save(category) // cascadeにより新しいアイテムも保存される
    return newItem
  }

  /**
   * カテゴリー内のアイテムを更新します。
   *
   * @param categoryId カテゴリーのID
   * @param itemId 更新するアイテムのID
   * @param itemJson アイテムの更新情報を含むJSON文字列
   * @param file アイテムの新しい画像データ（任意）
   * @param keepCurrentImage 現在の画像を保持するかどうか
   * @return 更新されたアイテム
   * @throws IllegalArgumentException カテゴリーが見つからない場合
   */
  @Transactional
  fun updateItemInCategory(
    categoryId: UUID,
    itemId: UUID,
    itemJson: String,
    file: MultipartFile?,
    keepCurrentImage: Boolean
  ): Item {
    val category =
      categoryRepository.findById(categoryId).orElseThrow {
        IllegalArgumentException("Category not found")
      }
    val itemName = ObjectMapper().readTree(itemJson).get("name").asText()
    val imageBytes = file?.bytes
    val updatedItem =
      category.updateItem(
        itemId = itemId,
        name = itemName,
        image = imageBytes,
        keepCurrentImage = keepCurrentImage,
        ""
      )
    categoryRepository.save(category) // cascadeによりアイテムの更新が反映される
    return updatedItem
  }
}
