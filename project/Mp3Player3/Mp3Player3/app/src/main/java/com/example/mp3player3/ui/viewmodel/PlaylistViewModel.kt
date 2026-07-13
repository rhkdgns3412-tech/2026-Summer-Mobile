@file:Suppress("unused")

package com.example.mp3player3.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mp3player3.model.Playlist
import com.example.mp3player3.model.Song

/**
 * PlaylistViewModel
 * - 사용자가 만든 플레이리스트 목록과 선택 상태를 관리합니다.
 * - 새 플레이리스트 생성, 곡 추가, 선택된 플레이리스트 변경을 담당합니다.
 */
class PlaylistViewModel : ViewModel() {
    private val _playlists = MutableLiveData<List<Playlist>>(emptyList())
    val playlists: LiveData<List<Playlist>> = _playlists

    private val _selectedPlaylist = MutableLiveData<Playlist?>(null)
    val selectedPlaylist: LiveData<Playlist?> = _selectedPlaylist

    /**
     * 새 플레이리스트를 추가합니다.
     * - 같은 이름은 허용하되, ID는 고유하게 생성합니다.
     */
    fun addPlaylist(title: String) {
        val trimmed = title.trim()
        if (trimmed.isEmpty()) return

        val newPlaylist = Playlist(
            id = System.currentTimeMillis(),
            title = trimmed,
            thumbnail = "",
            type = "플레이리스트",
            songs = emptyList()
        )

        val updated = _playlists.value.orEmpty() + newPlaylist
        _playlists.value = updated
        _selectedPlaylist.value = newPlaylist
    }

    /**
     * 플레이리스트를 선택합니다.
     */
    fun selectPlaylist(playlistId: Long) {
        _selectedPlaylist.value = _playlists.value.orEmpty().firstOrNull { it.id == playlistId }
    }

    /**
     * 선택된 플레이리스트에 곡을 추가합니다.
     */
    fun addSongsToSelectedPlaylist(songs: List<Song>) {
        val selected = _selectedPlaylist.value ?: return
        addSongsToPlaylist(selected.id, songs)
    }

    /**
     * 지정한 플레이리스트에 곡을 추가합니다.
     */
    fun addSongsToPlaylist(playlistId: Long, songs: List<Song>) {
        if (songs.isEmpty()) return

        val current = _playlists.value.orEmpty()
        val updated = current.map { playlist ->
            if (playlist.id == playlistId) {
                val merged = (playlist.songs + songs)
                    .distinctBy { it.id }
                playlist.copy(songs = merged)
            } else {
                playlist
            }
        }
        _playlists.value = updated
        _selectedPlaylist.value = updated.firstOrNull { it.id == playlistId }
    }

    /**
     * 현재 선택된 플레이리스트의 곡 목록을 반환합니다.
     */
    fun getSelectedPlaylistSongs(): List<Song> {
        return _selectedPlaylist.value?.songs.orEmpty()
    }
}
