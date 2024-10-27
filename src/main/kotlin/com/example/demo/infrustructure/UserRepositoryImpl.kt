package com.example.demo.domain.repository

import com.example.demo.domain.model.User
import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    override fun findAll(): List<User>
    override fun findById(id: Long): Optional<User>
    override fun save(user: User): User
    override fun deleteById(id: Long)
}
