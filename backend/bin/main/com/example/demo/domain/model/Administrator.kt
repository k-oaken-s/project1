package com.example.demo.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "administrator2")
data class Administrator(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false, unique = true)
    val username: String,

    @Column(nullable = false)
    val passwordHash: String
)
