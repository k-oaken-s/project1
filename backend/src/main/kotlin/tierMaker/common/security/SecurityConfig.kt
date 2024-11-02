package tierMaker.common.security

import tierMaker.common.security.JwtAuthenticationFilter
import tierMaker.common.security.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
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
                auth.requestMatchers("/admin/login")
                    .permitAll() // 認証なしでアクセス可能
                    .anyRequest()
                    .authenticated() // その他のリクエストは認証が必要
            }
            .addFilterBefore(
                jwtAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter::class.java
            ) // カスタムJWTフィルタを追加
        return http.build()
    }

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(JwtUtil)
    }

    // HttpSecurity用CORS設定
    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowedOriginPatterns = listOf("http://localhost:3000") // 許可するオリジンを指定
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS") // 許可するHTTPメソッドを指定
        config.allowedHeaders = listOf("Authorization", "Content-Type") // 許可するヘッダー
        config.allowCredentials = true // 認証情報を許可
        source.registerCorsConfiguration("/**", config)

//    val source = UrlBasedCorsConfigurationSource()
//    val config = CorsConfiguration()
//    // オリジンをワイルドカードで指定（ただし、allowCredentialsがtrueの場合は特定のオリジンを指定する必要があるため注意）
//    config.allowedOrigins = listOf("*")
//    // 許可するHTTPメソッドを全て指定
//    config.allowedMethods = listOf("*")
//    // 許可するヘッダーを全て指定
//    config.allowedHeaders = listOf("*")
//    // 認証情報の許可
//    config.allowCredentials = true
//    // CORS設定を全パスに適用
//    source.registerCorsConfiguration("/**", config);
        return source
    }
}
