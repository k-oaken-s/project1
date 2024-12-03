package rankifyHub.userTier.infrastructure.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import java.time.Instant
import org.springframework.stereotype.Repository
import rankifyHub.userTier.domain.model.UserTier
import rankifyHub.userTier.domain.repository.UserTierRepository

/** UserTierリポジトリのインフラ層実装 */
@Repository
class UserTierRepositoryImpl(
  private val userTierJpaRepository: UserTierJpaRepository,
  @PersistenceContext private val entityManager: EntityManager
) : UserTierRepository {

  override fun save(userTier: UserTier): UserTier {
    return userTierJpaRepository.save(userTier)
  }

  override fun findByIsPublicTrueOrderByCreatedAtDesc(): List<UserTier> {
    val query =
      entityManager.createQuery(
        "SELECT t FROM UserTier t WHERE t.isPublic = true ORDER BY t.createdAt DESC",
        UserTier::class.java
      )
    return query.resultList
  }

  override fun findLatest(limit: Int): List<UserTier> {
    val query =
      entityManager.createQuery(
        "SELECT t FROM UserTier t ORDER BY t.createdAt DESC",
        UserTier::class.java
      )
    return query.setMaxResults(limit).resultList
  }

  override fun findSince(timestamp: Instant): List<UserTier> {
    val query =
      entityManager.createQuery(
        "SELECT t FROM UserTier t WHERE t.createdAt > :timestamp ORDER BY t.createdAt DESC",
        UserTier::class.java
      )
    query.setParameter("timestamp", timestamp)
    return query.resultList
  }
}
