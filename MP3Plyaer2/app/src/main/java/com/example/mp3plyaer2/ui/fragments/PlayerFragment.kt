package com.example.mp3plyaer2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.mp3plyaer2.databinding.FragmentPlayerBinding
import com.example.mp3plyaer2.model.SongEntity
import com.example.mp3plyaer2.player.PlayerManager
import com.example.mp3plyaer2.player.PlayerManager.Companion.getInstance

/**
 * PlayerFragment: UI for playback. Uses PlayerManager (singleton) to control MediaPlayer.
 * Implements play/pause, prev/next, seekbar, shuffle, repeat and shows song metadata.
 */
class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    private lateinit var player: PlayerManager
    private var isTracking = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        player = getInstance(requireContext())

        // Observe playback state
        player.currentSong.observe(viewLifecycleOwner, Observer { song ->
            updateSongInfo(song)
        })
        player.isPlaying.observe(viewLifecycleOwner, Observer { playing ->
            updatePlayPauseButton(playing)
        })
        player.currentPosition.observe(viewLifecycleOwner, Observer { pos ->
            if (!isTracking) binding.seekBar.progress = pos
            binding.tvCurrent.text = formatTime(pos)
        })
        player.duration.observe(viewLifecycleOwner, Observer { dur ->
            binding.seekBar.max = dur
            binding.tvTotal.text = formatTime(dur)
        })

        player.shuffle.observe(viewLifecycleOwner) { enabled ->
            binding.btnShuffle.alpha = if (enabled) 1f else 0.5f
        }
        player.repeatMode.observe(viewLifecycleOwner) { mode ->
            binding.btnRepeat.alpha = when (mode) {
                PlayerManager.RepeatMode.NONE -> 0.5f
                else -> 1f
            }
        }

        // SeekBar handling
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) { isTracking = true }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                isTracking = false
                seekBar?.let { player.seekTo(it.progress) }
            }
        })

        // Controls
        binding.btnPlayPause.setOnClickListener { player.togglePlayPause() }
        binding.btnNext.setOnClickListener { player.next() }
        binding.btnPrev.setOnClickListener { player.previous() }
        binding.btnShuffle.setOnClickListener { player.toggleShuffle() }
        binding.btnRepeat.setOnClickListener { player.toggleRepeat() }
    }

    private fun updateSongInfo(song: SongEntity?) {
        if (song == null) {
            binding.tvTitle.text = ""
            binding.tvArtist.text = ""
            binding.ivArtwork.setImageResource(android.R.color.transparent)
            return
        }
        binding.tvTitle.text = song.title
        binding.tvArtist.text = song.artist
        // album art: not available in SongEntity; placeholder used
        binding.ivArtwork.setImageResource(com.example.mp3plyaer2.R.mipmap.ic_launcher_round)
    }

    private fun updatePlayPauseButton(isPlaying: Boolean) {
        binding.btnPlayPause.setImageResource(
            if (isPlaying) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play
        )
    }

    private fun formatTime(ms: Int): String {
        val totalSeconds = ms / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PlayerFragment()
    }
}

