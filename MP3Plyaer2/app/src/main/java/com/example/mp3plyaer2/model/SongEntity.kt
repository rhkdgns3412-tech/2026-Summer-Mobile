package com.example.mp3plyaer2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Song metadata stored in Room.
 */
@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val artist: String,
    val durationMs: Long,
    val uri: String? = null,
    val playCount: Int = 0,
    val addedAt: Long = System.currentTimeMillis()
)

