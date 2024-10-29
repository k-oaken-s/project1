package com.example.demo.application.service

import com.example.demo.domain.repository.CharacterRepository
import org.springframework.stereotype.Service
import com.example.demo.domain.model.Character

@Service
class CharacterService(private val characterRepository: CharacterRepository, private val gameService: GameService) {

    fun getCharactersByGameId(gameId: String): List<Character> = characterRepository.findByGameId(gameId)

    fun saveCharacter(gameId: String, character: Character): Character {
        val game = gameService.getGameById(gameId) ?: throw Exception("Game not found")
        val newCharacter = character.copy(game = game)
        return characterRepository.save(newCharacter)
    }

    fun deleteCharacter(id: String) = characterRepository.deleteById(id)
}
