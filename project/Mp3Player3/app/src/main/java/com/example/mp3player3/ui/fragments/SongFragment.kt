package com.example.mp3player3.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mp3player3.databinding.FragmentSongBinding
import com.example.mp3player3.ui.PlayerActivity
import com.example.mp3player3.ui.adapter.SongAdapter
import com.example.mp3player3.ui.viewmodel.SharedMusicViewModel

/**
 * SongFragment - 곡 목록 화면
 * - 곡 목록만 표시합니다.
 * - 곡을 클릭하면 별도의 `PlayerActivity`로 이동하여 재생합니다.
 * - 메인 화면에서는 재생 컨트롤을 보여주지 않습니다.
 */
class SongFragment : Fragment() {
    private var _binding: FragmentSongBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SharedMusicViewModel
    private lateinit var songAdapter: SongAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 메인 화면에서는 목록만 보여주므로 플레이어 관련 UI는 숨깁니다.
        binding.currentSongContainer.visibility = View.GONE
        binding.playerControlBar.visibility = View.GONE

        // 공유 ViewModel에서 곡 목록만 가져옵니다.
        viewModel = ViewModelProvider(requireActivity())[SharedMusicViewModel::class.java]

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        songAdapter = SongAdapter(
            onSongClick = { song ->
                // 곡을 클릭하면 별도 PlayerActivity를 열어서 재생합니다.
                val intent = Intent(requireContext(), PlayerActivity::class.java).apply {
                    putExtra("song_uri", song.uri)
                    putExtra("song_id", song.id)
                }
                startActivity(intent)
            },
            currentPlayingSongId = -1L
        )

        binding.songRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = songAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }

        viewModel.songList.observe(viewLifecycleOwner) { songs ->
            songAdapter.submitList(songs)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
