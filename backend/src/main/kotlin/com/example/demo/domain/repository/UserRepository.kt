package com.example.demo.domain.repository

import com.example.demo.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {}
