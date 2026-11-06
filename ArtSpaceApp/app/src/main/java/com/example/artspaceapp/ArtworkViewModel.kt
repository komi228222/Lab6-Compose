package com.example.artspaceapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ArtworkViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ArtworkUiState())
    val uiState: StateFlow<ArtworkUiState> = _uiState.asStateFlow()

    fun nextArtwork() {
        if (_uiState.value.currentArtworkIndex < 3) {
            _uiState.value = _uiState.value.copy(
                currentArtworkIndex = _uiState.value.currentArtworkIndex + 1
            )
        }
    }

    fun previousArtwork() {
        if (_uiState.value.currentArtworkIndex > 1) {
            _uiState.value = _uiState.value.copy(
                currentArtworkIndex = _uiState.value.currentArtworkIndex - 1
            )
        }
    }
}