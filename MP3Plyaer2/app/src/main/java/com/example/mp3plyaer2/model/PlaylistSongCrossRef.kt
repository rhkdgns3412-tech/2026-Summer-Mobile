package com.example.mp3plyaer2.model

import androidx.room.Entity

/**
 * Cross reference between a playlist and a song.
 */
@Entity(
    tableName = "playlist_song_cross_ref",
    primaryKeys = ["playlistId", "songId"]
)
data class PlaylistSongCrossRef(
    val playlistId: Long,
    val songId: Long,
    val orderIndex: Int
)

