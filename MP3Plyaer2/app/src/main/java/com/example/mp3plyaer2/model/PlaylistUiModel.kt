package com.example.mp3plyaer2.model

/**
 * UI model used by the playlist list adapter.
 */
data class PlaylistUiModel(
    val id: Long,
    val name: String,
    val songCount: Int,
    val type: PlaylistType,
    val isSmart: Boolean = false
)

