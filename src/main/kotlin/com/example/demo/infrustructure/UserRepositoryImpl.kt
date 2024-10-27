package com.example.demo.infrastructure.repository

import com.example.demo.domain.model.User
import com.example.demo.domain.repository.UserRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepositoryImpl : UserRepository, JpaRepository<User, Long> {
    override fun findAll(): List<User>
    override fun findById(id: Long): User?
    override fun save(user: User): User
    override fun deleteById(id: Long)
}
