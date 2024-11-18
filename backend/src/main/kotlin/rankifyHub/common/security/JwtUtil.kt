package rankifyHub.common.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.*
import javax.crypto.SecretKey

/**
 * JWT ユーティリティクラス
 *
 * JWT の生成、検証、データ抽出を提供する静的メソッドを含みます。
 */
object JwtUtil {
  /**
   * Base64 エンコードされた秘密鍵
   *
   * 安全な署名生成と検証に使用されます。
   */
  private const val BASE64_SECRET_KEY = "your-256-bit-secret-key-in-base64"

  /**
   * SecretKey オブジェクト
   *
   * BASE64 エンコードされたキーをデコードして生成されます。
   */
  private val secretKey: SecretKey = Keys.hmacShaKeyFor(BASE64_SECRET_KEY.toByteArray())

  /**
   * JWT トークンの生成
   *
   * 指定されたユーザー名を対象にしたトークンを生成します。 有効期限、発行日、署名情報が含まれます。
   *
   * @param username トークンに関連付けるユーザー名
   * @return 生成された JWT トークン
   */
  fun generateToken(username: String): String {
    val now = Date()
    val expiration = Date(now.time + 86400000) // トークンの有効期限を1日設定
    return Jwts.builder()
      .setSubject(username) // トークンの主体を設定（ユーザー名）
      .setIssuedAt(now) // 発行日時
      .setExpiration(expiration) // 有効期限
      .signWith(secretKey) // 署名に SecretKey を使用
      .compact() // トークンをコンパクト形式で生成
  }

  /**
   * JWT トークンの検証
   *
   * 指定されたトークンが有効かどうかを検証します。 検証には SecretKey を使用し、トークンの署名や構造が正しいか確認します。
   *
   * @param token 検証する JWT トークン
   * @return トークンが有効であれば true、無効であれば false
   */
  fun validateToken(token: String): Boolean =
    try {
      Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token) // トークンを検証
      true
    } catch (e: Exception) {
      false // 検証に失敗した場合
    }

  /**
   * JWT トークンからユーザー名を取得
   *
   * トークンの主体（subject）として保存されているユーザー名を抽出します。
   *
   * @param token 抽出元の JWT トークン
   * @return トークンに関連付けられたユーザー名
   */
  fun getUsernameFromToken(token: String): String =
    Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
}
