package com.example.mp3player3.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mp3player3.model.Song
import com.example.mp3player3.player.MusicPlayer
import com.example.mp3player3.repository.MusicRepository

/**
 * SharedMusicViewModel
 * - 전체 앱에서 공유하는 음악 플레이어 상태 관리
 * - 현재 재생 중인 곡, 재생 상태, 진행률 등을 관리
 * - MainActivity에서 전체 Fragment와 공유
 */
class SharedMusicViewModel(application: Application) : AndroidViewModel(application) {
    private val context: Context = application.applicationContext
    private val repository = MusicRepository()
    private val musicPlayer = MusicPlayer(context)

    // 현재 재생 중인 곡
    private val _currentSong = MutableLiveData<Song?>(null)
    val currentSong: LiveData<Song?> get() = _currentSong

    // 재생 상태 (true = 재생 중, false = 일시정지/중지)
    private val _isPlaying = MutableLiveData<Boolean>(false)
    val isPlaying: LiveData<Boolean> get() = _isPlaying

    // 현재 재생 위치 (밀리초)
    private val _currentPosition = MutableLiveData<Int>(0)
    val currentPosition: LiveData<Int> get() = _currentPosition

    // 곡의 총 길이 (밀리초)
    private val _duration = MutableLiveData<Int>(0)
    val duration: LiveData<Int> get() = _duration

    // 전체 곡 목록
    private val _songList = MutableLiveData<List<Song>>(emptyList())
    val songList: LiveData<List<Song>> get() = _songList

    // 현재 재생 중인 곡의 인덱스
    private val _currentSongIndex = MutableLiveData<Int>(-1)
    val currentSongIndex: LiveData<Int> get() = _currentSongIndex

    init {
        setupMusicPlayerCallbacks()
        loadSongs()
    }

    /**
     * MusicPlayer의 콜백 설정
     */
    private fun setupMusicPlayerCallbacks() {
        musicPlayer.onPlayStatusChanged = { isPlaying ->
            _isPlaying.postValue(isPlaying)
        }

        musicPlayer.onProgressChanged = { current, duration ->
            _currentPosition.postValue(current)
            _duration.postValue(duration)
        }

        musicPlayer.onSongComplete = {
            playNext()
        }
    }

    /**
     * 곡 목록 로드
     */
    private fun loadSongs() {
        val songs = repository.getAllSongs(context)
        _songList.postValue(songs)
    }

    /**
     * 지정된 곡 재생
     */
    fun playSong(song: Song) {
        val uri = song.uri ?: return
        _currentSong.postValue(song)
        val index = _songList.value?.indexOfFirst { it.id == song.id } ?: -1
        _currentSongIndex.postValue(index)
        musicPlayer.play(uri)
    }

    /**
     * 곡 목록의 인덱스로 재생
     */
    fun playSongAtIndex(index: Int) {
        val songs = _songList.value ?: return
        if (index in songs.indices) {
            playSong(songs[index])
        }
    }

    /**
     * 재생/일시정지 토글
     */
    fun togglePlayPause() {
        if (musicPlayer.isPlaying()) {
            musicPlayer.pause()
        } else {
            _currentSong.value?.let { song ->
                song.uri?.let { musicPlayer.play(it) }
            } ?: run {
                // 선택된 곡이 없으면 첫 번째 곡 재생
                _songList.value?.firstOrNull()?.let { playSong(it) }
            }
        }
    }

    /**
     * 다음 곡 재생
     */
    fun playNext() {
        val currentIndex = _currentSongIndex.value ?: -1
        val nextIndex = currentIndex + 1
        val songs = _songList.value ?: return

        if (nextIndex < songs.size) {
            playSongAtIndex(nextIndex)
        }
    }

    /**
     * 이전 곡 재생
     */
    fun playPrevious() {
        val currentIndex = _currentSongIndex.value ?: -1
        val previousIndex = currentIndex - 1
        val songs = _songList.value ?: return

        if (previousIndex >= 0) {
            playSongAtIndex(previousIndex)
        }
    }

    /**
     * 시간 위치로 이동
     */
    fun seekTo(position: Int) {
        musicPlayer.seekTo(position)
    }

    /**
     * 재생 중지
     */
    fun stop() {
        musicPlayer.stop()
        _isPlaying.postValue(false)
        _currentSong.postValue(null)
        _currentPosition.postValue(0)
        _currentSongIndex.postValue(-1)
    }

    override fun onCleared() {
        super.onCleared()
        musicPlayer.release()
    }
}

