package rankifyHub.category.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import rankifyHub.category.domain.model.Item
import rankifyHub.category.domain.repository.ItemRepository

/**
 * Itemリポジトリの実装
 */
@Repository
class ItemRepositoryImpl(
    private val jpaRepository: JpaRepository<Item, String>
) : ItemRepository {

    @Query("SELECT i FROM Item i ORDER BY i.createdAt DESC")
    override fun findLatest(limit: Int): List<Item> {
        return jpaRepository.findAll().sortedByDescending { it.createdAt }.take(limit)
    }

    @Query("SELECT i FROM Item i WHERE i.someField = :condition")
    override fun findByCustomCondition(condition: String): List<Item> {
        // 条件に基づくクエリの実行
        return jpaRepository.findAll().filter { it.someField == condition }
    }
}
