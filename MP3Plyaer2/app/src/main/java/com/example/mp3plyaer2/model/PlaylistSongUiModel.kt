package com.example.mp3plyaer2.model

/**
 * UI model for songs inside a playlist detail screen.
 */
data class PlaylistSongUiModel(
    val id: Long,
    val title: String,
    val artist: String,
    val durationText: String
)

