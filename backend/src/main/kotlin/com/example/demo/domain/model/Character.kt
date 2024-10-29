package com.example.demo.domain.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "character")
data class Character(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    val game: Game,

    @Column(nullable = false)
    val name: String,

    val imageUrl: String? = null,
    val description: String? = null
)
