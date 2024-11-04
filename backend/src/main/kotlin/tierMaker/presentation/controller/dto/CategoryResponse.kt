package tierMaker.presentation.controller.dto

data class CategoryResponse(
    val id: String,
    val name: String,
    val description: String?,
    val image: String?
)
