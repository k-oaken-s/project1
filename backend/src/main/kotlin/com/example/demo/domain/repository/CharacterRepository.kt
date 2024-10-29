package com.example.demo.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.example.demo.domain.model.Character


@Repository
interface CharacterRepository : JpaRepository<Character, String> {
    fun findByGameId(gameId: String): List<Character>
}
