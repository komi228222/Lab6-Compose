package com.example.unscramble.ui

import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.getUnscrambledWord
import org.junit.Assert.*
import org.junit.Test

class GameViewModelTest {
    private val viewModel = GameViewModel()

    // 1. Путь успеха: правильное слово
    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value
        assertFalse(currentGameUiState.isGuessedWordWrong)
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
    }

    // 2. Путь ошибки: неправильное слово
    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        val currentGameUiState = viewModel.uiState.value
        val incorrectPlayerWord = "qwerty"

        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()

        val updatedState = viewModel.uiState.value
        assertTrue(updatedState.isGuessedWordWrong)
        assertEquals(0, updatedState.score)
    }

    // 3. Граничный случай: инициализация
    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        assertFalse(currentGameUiState.isGuessedWordWrong)
        assertEquals(0, currentGameUiState.score)
        assertEquals(1, currentGameUiState.currentWordCount)
        assertNotEquals("", correctPlayerWord)
    }

    // 4. Все слова угаданы (окончание игры)
    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
        var currentGameUiState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        repeat(MAX_NO_OF_WORDS) {
            viewModel.updateUserGuess(correctPlayerWord)
            viewModel.checkUserGuess()
            currentGameUiState = viewModel.uiState.value
            correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
        }

        assertTrue(currentGameUiState.isGameOver)
        assertEquals(MAX_NO_OF_WORDS * SCORE_INCREASE, currentGameUiState.score)
    }

    // 5. Пропуск слова
    @Test
    fun gameViewModel_WordSkipped_ScoreUnchangedAndWordCountIncreased() {
        var currentGameUiState = viewModel.uiState.value
        val currentScore = currentGameUiState.score
        val currentWordCount = currentGameUiState.currentWordCount

        viewModel.skipWord()

        currentGameUiState = viewModel.uiState.value
        assertEquals(currentScore, currentGameUiState.score)
        assertEquals(currentWordCount + 1, currentGameUiState.currentWordCount)
    }

    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }
}