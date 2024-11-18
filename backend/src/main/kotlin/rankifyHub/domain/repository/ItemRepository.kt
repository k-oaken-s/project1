package rankifyHub.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rankifyHub.domain.model.Item

/**
 * Item Repository Interface
 *
 * @see JpaRepository
 * @see Category
 */
@Repository
internal interface ItemRepository : JpaRepository<Item, String> {
  fun findByCategoryId(categoryId: String): List<Item>
}
