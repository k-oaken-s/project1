package rankifyHub.category.presentation.dto

import java.util.*
import java.util.Collections.emptyList

data class CategoryResponse(
  val id: UUID,
  val name: String,
  val description: String?,
  val image: String?,
  val items: List<ItemResponse> = emptyList()
)
