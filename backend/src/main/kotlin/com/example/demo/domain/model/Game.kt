package com.example.demo.domain.model

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "game")
data class Game(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    val name: String,

    val description: String? = null,

    @OneToMany(mappedBy = "game", cascade = [CascadeType.ALL])
    val characters: List<Character> = emptyList()
)
