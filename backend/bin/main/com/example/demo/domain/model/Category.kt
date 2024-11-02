package com.example.demo.domain.model

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "category")
data class Category(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    val name: String = "",

    val description: String? = null,

    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL])
    val items: List<Item> = emptyList()
)
