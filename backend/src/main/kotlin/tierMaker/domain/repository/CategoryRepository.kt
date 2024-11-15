package tierMaker.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tierMaker.domain.model.Category

@Repository interface CategoryRepository : JpaRepository<Category, String>
