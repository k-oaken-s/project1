package com.example.demo.config

import com.example.demo.common.security.JwtAuthenticationFilter
import com.example.demo.common.security.JwtUtil
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
import org.springframework.web.filter.CorsFilter

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
            .cors { } // CORSを有効化
            .csrf { it.disable() } // CSRFを無効化
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/admin/login")
                    .permitAll() // 認証なしでアクセス可能
                    .requestMatchers("/admin/**")
                    .hasRole("ADMIN") // ロール"ADMIN"のユーザーのみアクセス可能
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

    // CORSの設定
    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowedOrigins = listOf("http://localhost:3000") // 許可するオリジンを指定
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS") // 許可するHTTPメソッドを指定
        config.allowedHeaders = listOf("Authorization", "Content-Type") // 許可するヘッダー
        config.allowCredentials = true // 認証情報を許可
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}
