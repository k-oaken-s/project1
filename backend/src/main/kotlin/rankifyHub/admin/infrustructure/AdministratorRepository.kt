package rankifyHub.admin.infrustructure

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rankifyHub.admin.domain.model.Administrator

@Repository
interface AdministratorRepository : JpaRepository<Administrator, String> {
  fun findByUsername(username: String): Administrator?
}
