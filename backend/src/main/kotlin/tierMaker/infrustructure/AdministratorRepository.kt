package tierMaker.infrustructure

import tierMaker.domain.model.Administrator
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdministratorRepository : JpaRepository<Administrator, String> {
    fun findByUsername(username: String): Administrator?
}
