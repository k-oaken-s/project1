package rankifyHub.presentation.controller.dto

data class CategoryResponse(
  val id: String,
  val name: String,
  val description: String?,
  val image: String?,
  val items: List<ItemResponse> = emptyList()
)
