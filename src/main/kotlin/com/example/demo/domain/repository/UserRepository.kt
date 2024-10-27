package com.example.demo.domain.repository

import com.example.demo.domain.model.User

interface UserRepository {
    fun findAll(): List<User>
    fun findById(id: Long): User?
    fun save(user: User): User
    fun deleteById(id: Long)
}
