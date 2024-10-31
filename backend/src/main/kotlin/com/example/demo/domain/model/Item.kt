package com.example.demo.domain.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "item")
data class Item(
    @Id val id: String = UUID.randomUUID().toString(),
    @ManyToOne @JoinColumn(name = "category_id", nullable = false) val category: Category,
    @Column(nullable = false) val name: String,
    val imageUrl: String? = null,
    val description: String? = null
)
