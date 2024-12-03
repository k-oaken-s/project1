package rankifyHub.category.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rankifyHub.category.domain.model.Category

/** Spring Data JPAによるCategoryリポジトリ */
@Repository interface CategoryJpaRepository : JpaRepository<Category, String>
