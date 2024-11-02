package tierMaker.common.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
        private val jwtUtil: JwtUtil // JwtUtilを注入してトークン検証とパースを行う
) : OncePerRequestFilter() {

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val token = authorizationHeader.substring(7)
            if (JwtUtil.validateToken(token)) {
                val username = JwtUtil.getUsernameFromToken(token)

                // 認証情報を設定
                val authToken =
                        UsernamePasswordAuthenticationToken(
                                User(username, "", listOf()),
                                null,
                                listOf()
                        )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                // SecurityContextに設定してリクエストを続行
                SecurityContextHolder.getContext().authentication = authToken
            }
        }

        filterChain.doFilter(request, response)
    }
}
