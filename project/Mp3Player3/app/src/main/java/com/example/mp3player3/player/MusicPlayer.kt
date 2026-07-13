package com.example.mp3player3.player

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri

/**
 * MusicPlayer - MediaPlayer 래퍼
 * - 음악 재생, 일시정지, 중지, 시간 조절 등 기본 제어 기능 제공
 * - 콜백 리스너를 통해 상태 업데이트 전달
 * 
 * TODO: 실제 앱에서는 다음이 필요합니다:
 * - 백그라운드 재생 서비스
 * - 오디오 포커스 관리
 * - Notification 표시
 * - 헤드폰 연결 상태 처리
 */
class MusicPlayer(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private var currentSongUri: String = ""
    private var completed = false

    // 콜백 리스너
    var onPlayStatusChanged: ((isPlaying: Boolean) -> Unit)? = null
    var onProgressChanged: ((current: Int, duration: Int) -> Unit)? = null
    var onSongComplete: (() -> Unit)? = null

    /**
     * 음악 재생 (새 곡 또는 재개)
     */
    fun play(uri: String) {
        // 같은 곡을 다시 클릭한 경우 재개
        if (uri == currentSongUri && mediaPlayer != null && !isPlaying()) {
            if (completed) {
                mediaPlayer?.seekTo(0)
                completed = false
            }
            mediaPlayer?.start()
            onPlayStatusChanged?.invoke(true)
            startProgressUpdate()
            return
        }

        // 다른 곡 재생
        if (uri != currentSongUri) {
            stop()
            currentSongUri = uri
            try {
                mediaPlayer = MediaPlayer.create(context, uri.toUri())
                mediaPlayer?.apply {
                    completed = false
                    isLooping = false
                    // 곡이 끝나면 콜백 호출
                    setOnCompletionListener {
                        completed = true
                        onSongComplete?.invoke()
                    }
                    start()
                }
                onPlayStatusChanged?.invoke(true)
                startProgressUpdate()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 음악 일시정지
     */
    fun pause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                onPlayStatusChanged?.invoke(false)
            }
        }
    }

    /**
     * 일시정지된 음악 재개
     */
    fun resume() {
        mediaPlayer?.let {
            if (!it.isPlaying) {
                if (completed) {
                    it.seekTo(0)
                    completed = false
                }
                it.start()
                onPlayStatusChanged?.invoke(true)
                startProgressUpdate()
            }
        }
    }

    /**
     * 음악 중지 및 초기화
     */
    fun stop() {
        mediaPlayer?.let {
            if (it.isPlaying) it.stop()
            it.release()
        }
        mediaPlayer = null
        currentSongUri = ""
        completed = false
    }

    /**
     * 재생 위치 변경 (시크 바 드래그)
     */
    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    /**
     * 현재 재생 위치 (밀리초)
     */
    fun getCurrentPosition(): Int = mediaPlayer?.currentPosition ?: 0

    /**
     * 곡의 총 길이 (밀리초)
     */
    fun getDuration(): Int = mediaPlayer?.duration ?: 0

    /**
     * 현재 재생 중인지 여부
     */
    fun isPlaying(): Boolean = mediaPlayer?.isPlaying ?: false

    /**
     * 재생 진행률 주기적으로 업데이트
     */
    private var updateThread: Thread? = null
    
    private fun startProgressUpdate() {
        if (updateThread?.isAlive == true) return
        
        updateThread = Thread {
            while (isPlaying()) {
                try {
                    val current = getCurrentPosition()
                    val duration = getDuration()
                    onProgressChanged?.invoke(current, duration)
                    Thread.sleep(100)  // 0.1초마다 업데이트
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        updateThread?.start()
    }

    /**
     * 리소스 해제
     */
    fun release() {
        stop()
        updateThread?.interrupt()
    }
}

