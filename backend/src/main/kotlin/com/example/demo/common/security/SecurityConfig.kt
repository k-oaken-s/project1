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
}
