package com.example.mp3plyaer2.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mp3plyaer2.model.PlaylistEntity
import com.example.mp3plyaer2.model.PlaylistSummary

@Dao
interface PlaylistDao {

    @Query("SELECT p.id AS id, p.name AS name, p.createdAt AS createdAt, COUNT(r.songId) AS songCount FROM playlists p LEFT JOIN playlist_song_cross_ref r ON p.id = r.playlistId GROUP BY p.id, p.name, p.createdAt ORDER BY p.createdAt DESC")
    fun observePlaylistSummaries(): LiveData<List<PlaylistSummary>>

    @Query("SELECT * FROM playlists WHERE id = :playlistId LIMIT 1")
    fun observePlaylist(playlistId: Long): LiveData<PlaylistEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: PlaylistEntity): Long

    @Update
    suspend fun update(item: PlaylistEntity)

    @Delete
    suspend fun delete(item: PlaylistEntity)

    @Query("DELETE FROM playlists WHERE id = :playlistId")
    suspend fun deleteById(playlistId: Long)

    @Query("SELECT COUNT(*) FROM playlists")
    suspend fun countPlaylists(): Int
}

