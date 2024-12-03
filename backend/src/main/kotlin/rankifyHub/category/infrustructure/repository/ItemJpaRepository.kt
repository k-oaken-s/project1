package rankifyHub.category.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rankifyHub.category.domain.model.Item

/** Spring Data JPAによるItemリポジトリ */
@Repository interface ItemJpaRepository : JpaRepository<Item, String>
