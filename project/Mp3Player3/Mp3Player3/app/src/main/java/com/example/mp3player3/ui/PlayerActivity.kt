package com.example.mp3player3.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.mp3player3.R
import com.example.mp3player3.databinding.ActivityPlayerBinding
import com.example.mp3player3.player.MusicPlayer
import com.example.mp3player3.repository.MusicRepository
import com.example.mp3player3.repository.FavoritesRepository
import com.example.mp3player3.model.Song
import kotlin.random.Random
import java.util.Locale
import kotlin.math.max

/**
 * PlayerActivity - 별도의 Activity에서 재생을 담당합니다.
 * - Intent extras:
 *   - "song_uri" (String)
 *   - "song_id" (Long)
 *   - "playlist_songs" (ArrayList<Song>)
 *   - "current_index" (Int)
 */
class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding
    private lateinit var musicPlayer: MusicPlayer
    private val repo = MusicRepository()
    private val handler = Handler(Looper.getMainLooper())
    private var updateRunnable: Runnable? = null

    private var songList = emptyList<Song>()
    private var currentIndex = -1
    private var repeatCurrent = false
    private var isShuffle = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        musicPlayer = MusicPlayer(this)
        // Completion handler will respect repeat / shuffle state
        musicPlayer.onSongComplete = {
            runOnUiThread { handleSongCompletion() }
        }

        // UI wiring
        binding.btnClose.setOnClickListener { finish() }
        binding.btnPlayPause.setOnClickListener { togglePlayPause() }
        binding.btnNext.setOnClickListener { playNext() }
        binding.btnPrev.setOnClickListener { playPrevious() }
        binding.btnShuffle.setOnClickListener {
            isShuffle = !isShuffle
            updateShuffleButton()
        }

        binding.btnRepeat.setOnClickListener {
            repeatCurrent = !repeatCurrent
            updateRepeatButton()
        }

        binding.btnLike.setOnClickListener {
            val cur = songList.getOrNull(currentIndex)
            if (cur != null) {
                FavoritesRepository.toggle(cur)
                updateLikeButton(cur)
            }
        }

        // initialize like button state when song changes

        binding.seekBar.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
            private var userSeeking = false
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val duration = musicPlayer.getDuration()
                    val position = (progress * (duration.toLong())) / 100
                    musicPlayer.seekTo(position.toInt())
                }
            }

            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) { userSeeking = true }
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) { userSeeking = false }
        })

        // Start UI updater
        startProgressUpdater()
        updateShuffleButton()
        updateRepeatButton()

        // 1) Playlist-specific playback data first
        loadPlaybackSource()
        refreshUI()
    }

    @Suppress("UNCHECKED_CAST")
    private fun loadPlaybackSource() {
        val playlistSongs = intent.extras?.get("playlist_songs") as? ArrayList<Song>
        if (!playlistSongs.isNullOrEmpty()) {
            songList = playlistSongs
            currentIndex = intent.getIntExtra("current_index", 0).coerceIn(songList.indices)
            playCurrentAtIndex()
            return
        }

        // 2) Fallback: library playback from repository / single song
        songList = repo.getAllSongs(this)
        val uri = intent.getStringExtra("song_uri")
        val songId = intent.getLongExtra("song_id", -1L)
        currentIndex = songList.indexOfFirst { it.id == songId }
        if (currentIndex < 0 && !uri.isNullOrEmpty()) {
            currentIndex = songList.indexOfFirst { it.uri == uri }
        }

        if (!uri.isNullOrEmpty()) {
            musicPlayer.play(uri)
            // try to find song by uri to update like button
            val cur = songList.firstOrNull { it.uri == uri }
            if (cur != null) updateLikeButton(cur)
        } else if (currentIndex >= 0) {
            playCurrentAtIndex()
        }
    }

    private fun playCurrentAtIndex() {
        val song = songList.getOrNull(currentIndex) ?: return
        binding.tvTitle.text = song.title
        binding.tvArtist.text = song.artist
        val played = song.uri?.takeIf { it.isNotEmpty() }?.let {
            musicPlayer.play(it)
            true
        } ?: false
        updateLikeButton(song)
        updatePlayPauseButton(played || musicPlayer.isPlaying())
    }

    private fun refreshUI() {
        val cur = songList.getOrNull(currentIndex)
        binding.tvTitle.text = cur?.title ?: ""
        binding.tvArtist.text = cur?.artist ?: ""
    }

    private fun togglePlayPause() {
        if (musicPlayer.isPlaying()) {
            musicPlayer.pause()
            updatePlayPauseButton(false)
        } else {
            if (currentIndex >= 0 && musicPlayer.getDuration() == 0) {
                songList.getOrNull(currentIndex)?.uri?.takeIf { it.isNotEmpty() }?.let { musicPlayer.play(it) }
            } else {
                musicPlayer.resume()
            }
            updatePlayPauseButton(true)
        }
    }

    private fun playNext() {
        moveToNextSong(fromCompletion = false)
    }

    private fun playPrevious() {
        if (songList.isEmpty()) return
        val prev = currentIndex - 1
        if (prev >= 0) {
            currentIndex = prev
            playCurrentAtIndex()
            refreshUI()
        }
    }

    private fun handleSongCompletion() {
        moveToNextSong(fromCompletion = true)
    }

    private fun moveToNextSong(fromCompletion: Boolean) {
        if (songList.isEmpty()) {
            updatePlayPauseButton(false)
            return
        }

        if (fromCompletion && repeatCurrent) {
            playCurrentAtIndex()
            refreshUI()
            return
        }

        val nextIndex = if (isShuffle) {
            getRandomNextIndex()
        } else {
            val candidate = currentIndex + 1
            if (candidate in songList.indices) candidate else null
        }

        if (nextIndex != null) {
            currentIndex = nextIndex
            playCurrentAtIndex()
            refreshUI()
        } else {
            updatePlayPauseButton(false)
        }
    }

    private fun getRandomNextIndex(): Int? {
        if (songList.size <= 1) return null
        var next = currentIndex
        while (next == currentIndex) {
            next = Random.nextInt(songList.size)
        }
        return next
    }

    private fun updateLikeButton(song: Song) {
        val isFav = FavoritesRepository.isFavorite(song)
        if (isFav) {
            binding.btnLike.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            binding.btnLike.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }

    private fun updateShuffleButton() {
        binding.btnShuffle.alpha = if (isShuffle) 1.0f else 0.6f
        binding.btnShuffle.contentDescription = if (isShuffle) {
            "Shuffle on"
        } else {
            "Shuffle off"
        }
    }

    private fun updateRepeatButton() {
        binding.btnRepeat.alpha = if (repeatCurrent) 1.0f else 0.6f
        binding.btnRepeat.contentDescription = if (repeatCurrent) {
            "Repeat current song on"
        } else {
            "Repeat current song off"
        }
    }

    private fun updatePlayPauseButton(isPlaying: Boolean) {
        binding.btnPlayPause.setImageResource(
            if (isPlaying) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play
        )
    }

    private fun startProgressUpdater() {
        updateRunnable = Runnable {
            try {
                val current = musicPlayer.getCurrentPosition()
                val duration = musicPlayer.getDuration()
                if (duration > 0) {
                    val progress = (current * 100L / max(1, duration)).toInt()
                    binding.seekBar.progress = progress
                }
                binding.tvTime.text = getString(
                    R.string.player_time_text,
                    formatTime(current),
                    formatTime(duration)
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            handler.postDelayed(updateRunnable!!, 300)
        }
        handler.post(updateRunnable!!)
    }

    private fun formatTime(ms: Int): String {
        val totalSec = ms / 1000
        val min = totalSec / 60
        val sec = totalSec % 60
        return String.format(Locale.getDefault(), "%02d:%02d", min, sec)
    }

    override fun onDestroy() {
        super.onDestroy()
        updateRunnable?.let { handler.removeCallbacks(it) }
        musicPlayer.release()
    }
}
