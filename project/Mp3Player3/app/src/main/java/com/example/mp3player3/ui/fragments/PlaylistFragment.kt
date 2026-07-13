package com.example.mp3player3.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mp3player3.databinding.FragmentPlaylistBinding
import com.example.mp3player3.model.Song
import com.example.mp3player3.repository.MusicRepository
import com.example.mp3player3.ui.PlayerActivity
import com.example.mp3player3.ui.adapter.PlaylistCardAdapter
import com.example.mp3player3.ui.adapter.SongAdapter
import com.example.mp3player3.ui.viewmodel.PlaylistViewModel
import kotlin.collections.ArrayList

/**
 * PlaylistFragment
 * - 사용자가 플레이리스트를 추가하고, 선택한 플레이리스트 안의 곡을 관리합니다.
 * - 플레이리스트를 클릭하면 해당 플레이리스트의 곡 목록이 아래에 표시됩니다.
 * - 곡을 클릭하면 `PlayerActivity`가 열리고, 해당 플레이리스트의 곡만 재생합니다.
 */
class PlaylistFragment : Fragment() {
    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlaylistViewModel by viewModels()
    private val musicRepository = MusicRepository()

    private lateinit var playlistAdapter: PlaylistCardAdapter
    private lateinit var playlistSongAdapter: SongAdapter

    private var librarySongs: List<Song> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 라이브러리의 전체 곡 목록을 한 번 읽어옵니다.
        librarySongs = musicRepository.getAllSongs(requireContext())

        setupPlaylistRecyclerView()
        setupPlaylistSongRecyclerView()
        setupButtons()
        observeViewModel()
    }

    private fun setupPlaylistRecyclerView() {
        playlistAdapter = PlaylistCardAdapter { playlist ->
            viewModel.selectPlaylist(playlist.id)
        }

        binding.playlistRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = playlistAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupPlaylistSongRecyclerView() {
        playlistSongAdapter = SongAdapter(
            onSongClick = { song ->
                val selectedPlaylist = viewModel.selectedPlaylist.value
                val songs = selectedPlaylist?.songs.orEmpty()
                val index = songs.indexOfFirst { it.id == song.id }
                if (index >= 0) {
                    openPlayerForPlaylistSongs(songs, index)
                }
            },
            currentPlayingSongId = -1L
        )

        binding.playlistSongRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = playlistSongAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    private fun setupButtons() {
        binding.btnAddPlaylist.setOnClickListener {
            showAddPlaylistDialog()
        }

        binding.btnAddSongs.setOnClickListener {
            showAddSongsDialog()
        }
    }

    private fun observeViewModel() {
        viewModel.playlists.observe(viewLifecycleOwner) { playlists ->
            playlistAdapter.submitList(playlists)
            if (viewModel.selectedPlaylist.value == null && playlists.isNotEmpty()) {
                viewModel.selectPlaylist(playlists.first().id)
            }
        }

        viewModel.selectedPlaylist.observe(viewLifecycleOwner) { playlist ->
            playlistAdapter.selectedPlaylistId = playlist?.id ?: -1L
            binding.tvSelectedPlaylistTitle.text =
                if (playlist != null) "${playlist.title} (${playlist.songCount}곡)" else "선택된 플레이리스트"

            val songs = playlist?.songs.orEmpty()
            playlistSongAdapter.submitList(songs)
            binding.tvEmptyPlaylist.visibility = if (playlist == null || songs.isEmpty()) View.VISIBLE else View.GONE
            binding.btnAddSongs.isEnabled = playlist != null
        }
    }

    private fun showAddPlaylistDialog() {
        val input = EditText(requireContext()).apply {
            hint = "플레이리스트 이름"
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
            setPadding(48, 32, 48, 32)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("플레이리스트 추가")
            .setView(input)
            .setPositiveButton("추가") { _, _ ->
                val title = input.text.toString()
                if (title.isBlank()) {
                    Toast.makeText(requireContext(), "이름을 입력하세요.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                viewModel.addPlaylist(title)
            }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun showAddSongsDialog() {
        val selectedPlaylist = viewModel.selectedPlaylist.value
        if (selectedPlaylist == null) {
            Toast.makeText(requireContext(), "먼저 플레이리스트를 선택하세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (librarySongs.isEmpty()) {
            Toast.makeText(requireContext(), "추가할 곡이 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val items = librarySongs.map { "${it.title} - ${it.artist}" }.toTypedArray()
        val checked = BooleanArray(librarySongs.size) { index ->
            selectedPlaylist.songs.any { it.id == librarySongs[index].id }
        }

        AlertDialog.Builder(requireContext())
            .setTitle("플레이리스트에 곡 추가")
            .setMultiChoiceItems(items, checked) { _, which, isChecked ->
                checked[which] = isChecked
            }
            .setPositiveButton("추가") { _, _ ->
                val songsToAdd = librarySongs.filterIndexed { index, _ -> checked[index] }
                viewModel.addSongsToSelectedPlaylist(songsToAdd)
                Toast.makeText(requireContext(), "곡을 추가했습니다.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun openPlayerForPlaylistSongs(songs: List<Song>, index: Int) {
        val intent = Intent(requireContext(), PlayerActivity::class.java).apply {
            putExtra("playlist_songs", ArrayList(songs))
            putExtra("current_index", index)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
