package rankifyHub.presentation.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rankifyHub.common.security.JwtUtil
import rankifyHub.presentation.controller.dto.LoginRequest

/** RESTコントローラー: 管理者認証を処理します。 */
@RestController
@RequestMapping("/admin")
class AdminController {

  /**
   * 管理者ログイン用のエンドポイント。
   *
   * このメソッドは、指定されたユーザー名とパスワードを使用して認証を行い、成功した場合はJWTトークンを返します。
   *
   * @param loginRequest ログインリクエスト（ユーザー名とパスワード）
   * @return 認証に成功した場合はJWTトークンを含むレスポンス。それ以外の場合は401ステータスコード。
   *
   * TODO: ユーザー名は "admin"、パスワードは "password" に固定されている
   */
  @PostMapping("/login")
  fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Map<String, String>> {
    if (loginRequest.username == "admin" && loginRequest.password == "password") {
      val token = JwtUtil.generateToken(loginRequest.username)
      return ResponseEntity.ok(mapOf("token" to token))
    }
    return ResponseEntity.status(401).build()
  }
}
