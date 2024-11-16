package tierMaker.application.admin

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import tierMaker.infrustructure.AdministratorRepository

/**
 * 管理者認証サービス
 *
 * Spring Security の `UserDetailsService` を実装し、管理者の認証情報を提供します。
 */
@Service
class AdminDetailsService(private val administratorRepository: AdministratorRepository) :
  UserDetailsService {

  /**
   * ユーザー名を基に管理者の認証情報をロード
   *
   * 指定されたユーザー名を使用して管理者のデータをデータベースから取得し、
   * Spring Security の認証プロセスで使用する `UserDetails` オブジェクトを生成します。
   *
   * @param username 認証対象の管理者のユーザー名
   * @return `UserDetails` オブジェクト（管理者の認証情報を含む）
   * @throws UsernameNotFoundException ユーザー名が見つからない場合にスローされる例外
   */
  override fun loadUserByUsername(username: String): UserDetails {
    val admin =
      administratorRepository.findByUsername(username)
        ?: throw UsernameNotFoundException("Administrator not found")
    return User(admin.username, admin.passwordHash, listOf(SimpleGrantedAuthority("ROLE_ADMIN")))
  }
}
