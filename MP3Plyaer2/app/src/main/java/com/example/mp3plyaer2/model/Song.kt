package com.example.mp3plyaer2.model

/** Simple data model representing a song. */
data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val uri: String? = null
)

