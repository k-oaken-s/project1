package tierMaker.application.admin

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import tierMaker.infrustructure.AdministratorRepository

@Service
class AdminDetailsService(private val administratorRepository: AdministratorRepository) :
  UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails {
    val admin =
      administratorRepository.findByUsername(username)
        ?: throw UsernameNotFoundException("Administrator not found")
    return User(admin.username, admin.passwordHash, listOf(SimpleGrantedAuthority("ROLE_ADMIN")))
  }
}
