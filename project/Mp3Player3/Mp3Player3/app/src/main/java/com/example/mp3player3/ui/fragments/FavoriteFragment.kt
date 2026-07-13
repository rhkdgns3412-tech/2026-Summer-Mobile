package com.example.mp3player3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mp3player3.databinding.FragmentFavoriteBinding
import com.example.mp3player3.repository.FavoritesRepository
import com.example.mp3player3.ui.PlayerActivity
import com.example.mp3player3.ui.adapter.SongAdapter


/**
 * FavoriteFragment - 좋아요 화면
 * - 사용자가 좋아요 표시한 곡들을 보여줍니다.
 * - RecyclerView를 통해 좋아요 곡 목록을 표시합니다.
 */
class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 초기화 작업
        setupUI()
        observeFavorites()
    }

    private fun setupUI() {
        // RecyclerView 설정
        binding.favoriteRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SongAdapter(onSongClick = { song ->
                // 재생: 즐겨찾기 전체를 플레이리스트로 전달
                val favs = FavoritesRepository.favorites.value.orEmpty()
                val index = favs.indexOfFirst { it.id == song.id }
                if (index >= 0) {
                    val intent = Intent(requireContext(), PlayerActivity::class.java).apply {
                        putExtra("playlist_songs", ArrayList(favs))
                        putExtra("current_index", index)
                    }
                    startActivity(intent)
                }
            })
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
        binding.emptyText.text = "좋아요 곡이 없습니다"
    }

    private fun observeFavorites() {
        FavoritesRepository.favorites.observe(viewLifecycleOwner) { list ->
            val adapter = binding.favoriteRecyclerView.adapter as? SongAdapter
            adapter?.submitList(list)
            binding.emptyText.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

