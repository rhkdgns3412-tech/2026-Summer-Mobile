package com.example.mp3plyaer2.repository

import com.example.mp3plyaer2.model.Song

/**
 * Simple repository that returns sample songs. In a real app this would query MediaStore or a DB.
 */
object SongRepository {
    fun getSampleSongs(): List<Song> {
        return listOf(
            Song(1, "Song A", "Artist 1"),
            Song(2, "Song B", "Artist 2"),
            Song(3, "Song C", "Artist 3"),
            Song(4, "Song D", "Artist 4")
        )
    }
}

