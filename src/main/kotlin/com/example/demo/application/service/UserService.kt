package com.example.demo.application.service

import com.example.demo.domain.model.User
import com.example.demo.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun getAllUsers(): List<User> = userRepository.findAll()
    fun getUserById(id: Long): User? = userRepository.findById(id)
    fun saveUser(user: User): User = userRepository.save(user)
    fun deleteUserById(id: Long) = userRepository.deleteById(id)
}
