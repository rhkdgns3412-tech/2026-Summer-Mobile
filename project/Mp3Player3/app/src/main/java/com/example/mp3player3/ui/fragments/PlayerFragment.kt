package com.example.mp3player3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mp3player3.databinding.FragmentPlayerBinding
import com.example.mp3player3.ui.viewmodel.SharedMusicViewModel

/**
 * PlayerFragment - 전체 화면 재생 화면
 * - SharedMusicViewModel을 참조하여 재생 제어 및 진행률 표시
 */
class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SharedMusicViewModel
    private var isDragging = false

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
        viewModel = ViewModelProvider(requireActivity()).get(SharedMusicViewModel::class.java)

        // Close button
        binding.btnClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Control buttons
        binding.btnPlayPause.setOnClickListener {
            viewModel.togglePlayPause()
        }
        binding.btnNext.setOnClickListener { viewModel.playNext() }
        binding.btnPrev.setOnClickListener { viewModel.playPrevious() }

        // SeekBar
        binding.seekBar.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val duration = viewModel.duration.value ?: 0
                    val position = (progress * (duration.toLong())) / 100
                    viewModel.seekTo(position.toInt())
                }
            }

            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {
                isDragging = true
            }

            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {
                isDragging = false
            }
        })

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.currentSong.observe(viewLifecycleOwner) { song ->
            if (song != null) {
                binding.tvTitle.text = song.title
                binding.tvArtist.text = song.artist
            } else {
                binding.tvTitle.text = ""
                binding.tvArtist.text = ""
            }
        }

        viewModel.isPlaying.observe(viewLifecycleOwner) { isPlaying ->
            binding.btnPlayPause.setImageResource(
                if (isPlaying) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play
            )
        }

        viewModel.currentPosition.observe(viewLifecycleOwner) { current ->
            if (!isDragging) {
                val duration = viewModel.duration.value ?: 0
                val progress = if (duration > 0) (current * 100) / duration else 0
                binding.seekBar.progress = progress
            }
            val curStr = formatTime(current)
            val durStr = formatTime(viewModel.duration.value ?: 0)
            binding.tvTime.text = "$curStr / $durStr"
        }
    }

    private fun formatTime(ms: Int): String {
        val totalSec = ms / 1000
        val min = totalSec / 60
        val sec = totalSec % 60
        return String.format("%02d:%02d", min, sec)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

