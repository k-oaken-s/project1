package rankifyHub.category.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rankifyHub.category.domain.model.Category
import rankifyHub.category.domain.repository.CategoryRepository
import java.util.*

/**
 * Categoryリポジトリのインフラ層実装
 */
@Repository
class CategoryRepositoryImpl(
    private val jpaRepository: JpaRepository<Category, String>
) : CategoryRepository {

    override fun findAll(): List<Category> = jpaRepository.findAll()

    override fun findById(id: String): Optional<Category> = jpaRepository.findById(id)

    override fun save(category: Category): Category = jpaRepository.save(category)

    override fun deleteById(id: String) = jpaRepository.deleteById(id)
}
