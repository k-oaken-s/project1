package com.example.demo.domain.repository

import com.example.demo.domain.model.User
import java.util.Optional

interface UserRepository {
    fun findAll(): List<User>
    fun findById(id: Long): Optional<User>
    fun save(user: User): User
    fun deleteById(id: Long)
}
