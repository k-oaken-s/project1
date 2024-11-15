package tierMaker.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tierMaker.domain.model.Item

@Repository
internal interface ItemRepository : JpaRepository<Item, String> {
  fun findByCategoryId(categoryId: String): List<Item>
}
