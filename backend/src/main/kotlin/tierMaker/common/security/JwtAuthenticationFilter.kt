package tierMaker.common.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

/**
 * フィルタークラス: JWT 認証フィルター
 *
 * このフィルターは、リクエストの Authorization ヘッダーに含まれる JWT を検証し、
 * ユーザー情報を Spring Security の SecurityContext に設定します。
 */
class JwtAuthenticationFilter(
  private val jwtUtil: JwtUtil // JwtUtilを注入してトークン検証とパースを行う
) : OncePerRequestFilter() {

  /**
   * リクエスト内の JWT を検証し、認証コンテキストに設定します。
   *
   * @param request HttpServletRequest オブジェクト
   * @param response HttpServletResponse オブジェクト
   * @param filterChain フィルターチェーンオブジェクト
   */
  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain
  ) {
    // Authorization ヘッダーからトークンを取得
    val authorizationHeader = request.getHeader("Authorization")

    // トークンが存在し "Bearer " で始まる場合
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      val token = authorizationHeader.substring(7) // "Bearer " の部分を除去
      if (JwtUtil.validateToken(token)) { // トークンの検証
        val username = JwtUtil.getUsernameFromToken(token) // トークンからユーザー名を取得

        // 認証情報を作成
        val authToken =
          UsernamePasswordAuthenticationToken(User(username, "", listOf()), null, listOf())
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

        // SecurityContext に認証情報を設定
        SecurityContextHolder.getContext().authentication = authToken
      }
    }

    // フィルターチェーンを続行
    filterChain.doFilter(request, response)
  }
}
