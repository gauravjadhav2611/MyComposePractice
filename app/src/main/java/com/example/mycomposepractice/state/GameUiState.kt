package com.example.mycomposepractice.state

import com.example.mycomposepractice.model.Game

data class GameUiState(
    val isLoading: Boolean = false,
    val games: List<Game> = emptyList(),
    val error: String? = null
)