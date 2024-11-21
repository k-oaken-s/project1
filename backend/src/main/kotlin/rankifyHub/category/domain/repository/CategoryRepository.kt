package rankifyHub.category.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rankifyHub.category.domain.model.Category

/**
 * Category Repository Interface
 *
 * @see JpaRepository
 * @see Category
 */
@Repository interface CategoryRepository : JpaRepository<Category, String>
