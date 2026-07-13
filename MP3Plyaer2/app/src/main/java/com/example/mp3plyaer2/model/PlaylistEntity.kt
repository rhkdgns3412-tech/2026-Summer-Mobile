package com.example.mp3plyaer2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * User-created playlist stored in Room.
 */
@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)

