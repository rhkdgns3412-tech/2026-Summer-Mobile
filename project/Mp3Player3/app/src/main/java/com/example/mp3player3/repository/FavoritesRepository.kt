package com.example.mp3player3.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mp3player3.model.Song

/**
 * FavoritesRepository - 앱 전역에서 좋아요(즐겨찾기) 곡을 관리하는 단순 저장소
 * - 메모리 기반의 LiveData로 상태를 제공합니다.
 */
object FavoritesRepository {
    private val _favorites = MutableLiveData<List<Song>>(emptyList())
    val favorites: LiveData<List<Song>> = _favorites

    fun add(song: Song) {
        val current = _favorites.value.orEmpty()
        if (current.any { it.id == song.id }) return
        _favorites.value = current + song
    }

    fun remove(song: Song) {
        val current = _favorites.value.orEmpty()
        _favorites.value = current.filter { it.id != song.id }
    }

    fun toggle(song: Song) {
        val current = _favorites.value.orEmpty()
        if (current.any { it.id == song.id }) {
            _favorites.value = current.filter { it.id != song.id }
        } else {
            _favorites.value = current + song
        }
    }

    fun isFavorite(song: Song?): Boolean {
        if (song == null) return false
        return _favorites.value.orEmpty().any { it.id == song.id }
    }
}

