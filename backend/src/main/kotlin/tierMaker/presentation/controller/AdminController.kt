package tierMaker.presentation.controller

import tierMaker.application.category.CategoryUseCase
import tierMaker.common.security.JwtUtil
import tierMaker.presentation.controller.dto.LoginRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class AdminController() {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Map<String, String>> {
        if (loginRequest.username == "admin" && loginRequest.password == "password") {
            val token = JwtUtil.generateToken(loginRequest.username)
            return ResponseEntity.ok(mapOf("token" to token))
        }
        return ResponseEntity.status(401).build()
    }
}
