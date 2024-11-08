package tierMaker.common.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .cors { it.configurationSource(corsConfigurationSource()) } // CORS設定を適用
                .csrf { it.disable() } // CSRFを無効化
                .authorizeHttpRequests { auth ->
                    auth.anyRequest().permitAll() // 全てのリクエストを許可
                }
        return http.build()
    }

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(JwtUtil)
    }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        // すべてのオリジンを許可
        config.allowedOriginPatterns = listOf("*")

        // すべてのHTTPメソッドを許可
        config.allowedMethods = listOf("*")

        // すべてのヘッダーを許可
        config.allowedHeaders = listOf("*")

        // 認証情報を無効にする
        config.allowCredentials = false

        // すべてのパスにCORS設定を適用
        source.registerCorsConfiguration("/**", config)
        return source
    }
}
