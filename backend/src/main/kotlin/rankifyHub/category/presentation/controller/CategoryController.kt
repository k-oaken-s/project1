package rankifyHub.category.presentation.controller

import java.util.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import rankifyHub.category.application.AddCategoryDto
import rankifyHub.category.application.CategoryUseCase
import rankifyHub.category.presentation.dto.CategoryResponse
import rankifyHub.category.presentation.dto.ItemResponse
import rankifyHub.shared.infrustructure.PublicFileStorageRepository

/** RESTコントローラー: カテゴリおよびカテゴリ内のアイテムを管理します。 */
@RestController
@RequestMapping("/categories")
class CategoryController(
  private val categoryUseCase: CategoryUseCase,
  private val publicFileStorageRepository: PublicFileStorageRepository
) {

  /**
   * 全てのカテゴリを取得します。
   *
   * @return カテゴリ一覧のレスポンス
   */
  @GetMapping
  fun getAllCategories(): ResponseEntity<List<CategoryResponse>> {
    val categories = categoryUseCase.getAllCategories()
    val response =
      categories.map { category ->
        CategoryResponse(
          id = category.id,
          name = category.name,
          description = category.description,
          image =
            category.image?.let {
              this.publicFileStorageRepository.saveFile("images", category.id.toString(), it, "jpg")
            }
        )
      }
    return ResponseEntity.ok(response)
  }

  /**
   * 指定されたIDのカテゴリと、そのカテゴリに関連付けられたアイテムを取得します。
   *
   * @param categoryId カテゴリID
   * @return 指定されたカテゴリとそのアイテムのレスポンス
   */
  @GetMapping("/{categoryId}")
  fun getCategoryWithItems(@PathVariable categoryId: UUID): ResponseEntity<CategoryResponse> {
    val category = categoryUseCase.getCategoryWithItems(categoryId)
    val response =
      CategoryResponse(
        id = category.id,
        name = category.name,
        description = category.description,
        image =
          category.image?.let {
            this.publicFileStorageRepository.saveFile("images", category.id.toString(), it, "jpg")
          },
        items =
          category.items.map { item ->
            ItemResponse(
              id = item.id,
              name = item.name,
              image =
                item.image?.let {
                  this.publicFileStorageRepository.saveFile(
                    "images",
                    category.id.toString() + item.id.toString(),
                    it,
                    "jpg"
                  )
                }
            )
          }
      )
    return ResponseEntity.ok(response)
  }

  /**
   * 新しいカテゴリを追加します。
   *
   * @param categoryDto カテゴリ情報のDTO
   * @param file カテゴリに関連付ける画像ファイル（任意）
   * @return 作成されたカテゴリのレスポンス
   */
  @PostMapping
  fun addCategory(
    @RequestPart("category") categoryDto: AddCategoryDto,
    @RequestPart("file", required = false) file: MultipartFile?
  ): ResponseEntity<CategoryResponse> {
    val category = categoryUseCase.addCategory(categoryDto, file?.bytes)
    val response =
      CategoryResponse(
        id = category.id,
        name = category.name,
        description = category.description,
        image =
          category.image?.let {
            this.publicFileStorageRepository.saveFile("images", category.id.toString(), it, "jpg")
          }
      )
    return ResponseEntity.ok(response)
  }

  /**
   * 指定されたカテゴリにアイテムを追加します。
   *
   * @param categoryId カテゴリID
   * @param itemJson アイテム情報のJSON文字列
   * @param file アイテムに関連付ける画像ファイル（任意）
   * @return 作成されたアイテムのレスポンス
   */
  @PostMapping("/{categoryId}/items")
  fun addItemToCategory(
    @PathVariable categoryId: UUID,
    @RequestPart("item") itemJson: String,
    @RequestPart(value = "file", required = false) file: MultipartFile?
  ): ResponseEntity<ItemResponse> {
    val item = categoryUseCase.addItemToCategory(categoryId, itemJson, file?.bytes)
    val response =
      ItemResponse(
        id = item.id,
        name = item.name,
        image =
          item.image?.let {
            this.publicFileStorageRepository.saveFile(
              "images",
              categoryId.toString() + item.id.toString(),
              it,
              "jpg"
            )
          }
      )
    return ResponseEntity.ok(response)
  }

  /**
   * 指定されたカテゴリ内のアイテムを更新します。
   *
   * @param categoryId カテゴリID
   * @param itemId アイテムID
   * @param itemJson 更新するアイテム情報のJSON文字列
   * @param file 更新する画像ファイル（任意）
   * @param keepCurrentImage 現在の画像を保持するかどうかのフラグ
   * @return 更新されたアイテムのレスポンス
   */
  @PutMapping("/{categoryId}/items/{itemId}")
  fun updateItemInCategory(
    @PathVariable categoryId: UUID,
    @PathVariable itemId: UUID,
    @RequestPart("item") itemJson: String,
    @RequestPart(value = "file", required = false) file: MultipartFile?,
    @RequestParam(value = "keepCurrentImage", defaultValue = "false") keepCurrentImage: Boolean
  ): ResponseEntity<ItemResponse> {
    val updatedItem =
      categoryUseCase.updateItemInCategory(categoryId, itemId, itemJson, file, keepCurrentImage)
    val response =
      ItemResponse(
        id = updatedItem.id,
        name = updatedItem.name,
        image =
          updatedItem.image?.let {
            this.publicFileStorageRepository.saveFile(
              "images",
              categoryId.toString() + updatedItem.id.toString(),
              it,
              "jpg"
            )
          }
      )
    return ResponseEntity.ok(response)
  }
}
