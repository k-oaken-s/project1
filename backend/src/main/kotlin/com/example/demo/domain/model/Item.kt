package com.example.demo.domain.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "item")
data class Item(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    val name: String = "",

    val description: String? = null,

    @Column(name = "image_url")
    val imageUrl: String? = null,

    @ManyToOne
    @JoinColumn(name = "category_id")
    val category: Category? = null
)
