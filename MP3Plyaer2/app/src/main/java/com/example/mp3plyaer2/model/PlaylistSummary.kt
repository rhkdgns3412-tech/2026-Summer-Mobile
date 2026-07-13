package com.example.mp3plyaer2.model

/**
 * Projection for displaying playlist rows with the song count.
 */
data class PlaylistSummary(
    val id: Long,
    val name: String,
    val createdAt: Long,
    val songCount: Int
)

