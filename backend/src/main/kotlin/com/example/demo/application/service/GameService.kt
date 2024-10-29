package com.example.demo.application.service

import com.example.demo.domain.model.Game
import com.example.demo.domain.repository.GameRepository
import org.springframework.stereotype.Service

@Service
class GameService(private val gameRepository: GameRepository) {

    fun getAllGames(): List<Game> = gameRepository.findAll()

    fun getGameById(id: String): Game? = gameRepository.findById(id).orElse(null)

    fun saveGame(game: Game): Game = gameRepository.save(game)

    fun deleteGame(id: String) = gameRepository.deleteById(id)
}
