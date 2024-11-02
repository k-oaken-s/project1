package tierMaker.common.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.*
import javax.crypto.SecretKey

object JwtUtil {
    // 事前に Base64 エンコードされたキーを読み込み、SecretKey オブジェクトを生成
    private const val BASE64_SECRET_KEY = "your-256-bit-secret-key-in-base64"
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(BASE64_SECRET_KEY.toByteArray())

    // トークン生成メソッド：短期間の有効期限、発行者、対象者を含む
    fun generateToken(username: String): String {
        val now = Date()
        val expiration = Date(now.time + 86400000) // トークンの有効期限を1日設定
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(secretKey) // 安全な SecretKey の使用
            .compact()
    }

    // トークン検証メソッド：署名キーで検証し、パースエラーをキャッチ
    fun validateToken(token: String): Boolean = try {
        Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
        true
    } catch (e: Exception) {
        false
    }

    // トークンからユーザー名を抽出するメソッド
    fun getUsernameFromToken(token: String): String =
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
}
