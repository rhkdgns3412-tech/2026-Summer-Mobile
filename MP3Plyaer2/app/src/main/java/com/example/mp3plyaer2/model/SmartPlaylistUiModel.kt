package com.example.mp3plyaer2.model

/**
 * UI model for smart playlists shown at the top of the screen.
 */
data class SmartPlaylistUiModel(
    val type: PlaylistType,
    val title: String,
    val subtitle: String,
    val songCount: Int
)

