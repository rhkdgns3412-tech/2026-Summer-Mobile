package com.example.mp3plyaer2.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mp3plyaer2.model.SongEntity

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<SongEntity>)

    @Query("SELECT * FROM songs ORDER BY addedAt DESC")
    fun observeAllSongs(): LiveData<List<SongEntity>>

    @Query("SELECT * FROM songs ORDER BY addedAt DESC LIMIT :limit")
    fun observeRecentlyAddedSongs(limit: Int): LiveData<List<SongEntity>>

    @Query("SELECT * FROM songs ORDER BY playCount DESC, addedAt DESC LIMIT :limit")
    fun observeMostPlayedSongs(limit: Int): LiveData<List<SongEntity>>

    @Query("SELECT s.* FROM songs s INNER JOIN playlist_song_cross_ref r ON s.id = r.songId WHERE r.playlistId = :playlistId ORDER BY r.orderIndex ASC")
    fun observeSongsInPlaylist(playlistId: Long): LiveData<List<SongEntity>>

    @Query("SELECT COUNT(*) FROM songs")
    suspend fun countSongs(): Int
}

