package rankifyHub.userTier.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rankifyHub.userTier.domain.model.UserTier
import java.util.*

/**
 * Spring Data JPAによる基本的なCRUD操作用のリポジトリ
 */
@Repository
interface UserTierJpaRepository : JpaRepository<UserTier, UUID>
