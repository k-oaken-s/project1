package com.example.demo.common.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*

object JwtUtil {
    private const val SECRET_KEY = "your-secret-key"

    fun generateToken(username: String): String {
        val now = Date()
        val expiration = Date(now.time + 86400000) // トークンの有効期限は1日
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.toByteArray())
                .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(SECRET_KEY.toByteArray()).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUsernameFromToken(token: String): String {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.toByteArray())
                .parseClaimsJws(token)
                .body
                .subject
    }
}
