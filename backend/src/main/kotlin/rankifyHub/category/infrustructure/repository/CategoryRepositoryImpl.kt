package rankifyHub.category.infrastructure.repository

import java.util.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rankifyHub.category.domain.model.Category
import rankifyHub.category.domain.repository.CategoryRepository

/** Categoryリポジトリのインフラ層実装 */
@Repository
class CategoryRepositoryImpl(private val jpaRepository: JpaRepository<Category, UUID>) :
  CategoryRepository {

  override fun findAll(): List<Category> = jpaRepository.findAll()

  override fun findById(id: UUID): Optional<Category> = jpaRepository.findById(id)

  override fun save(category: Category): Category = jpaRepository.save(category)

  override fun deleteById(id: UUID) = jpaRepository.deleteById(id)
}
