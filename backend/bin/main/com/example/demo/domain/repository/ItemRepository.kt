package com.example.demo.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.example.demo.domain.model.Item


@Repository
interface ItemRepository : JpaRepository<Item, String> {
    fun findByCategoryId(gameId: String): List<Item>
}
