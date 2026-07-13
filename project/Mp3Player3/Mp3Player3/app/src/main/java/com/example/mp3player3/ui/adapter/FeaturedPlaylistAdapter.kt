package com.example.mp3player3.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mp3player3.databinding.ItemFeaturedPlaylistBinding
import com.example.mp3player3.model.Playlist

/**
 * FeaturedPlaylistAdapter
 * - 가로 RecyclerView용 어댑터
 * - 최근에 추가한 곡, 많이 재생한 곡, 즐겨찾기 등 추천 플레이리스트 표시
 * - ListAdapter를 사용하여 효율적인 업데이트 처리
 */
class FeaturedPlaylistAdapter(
    private val onItemClick: (Playlist) -> Unit = {}
) : ListAdapter<Playlist, FeaturedPlaylistAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFeaturedPlaylistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHolder - 카드 아이템을 표시합니다.
     * - 둥근 모서리 배경
     * - 중앙 아이콘
     * - 플레이리스트 이름 및 곡 개수
     */
    inner class ViewHolder(
        private val binding: ItemFeaturedPlaylistBinding,
        private val onItemClick: (Playlist) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist) {
            // 타입별 아이콘 설정
            val iconResId = getIconForPlaylistType(playlist.type)
            binding.playlistIcon.setImageResource(iconResId)
            
            // 플레이리스트 이름 설정
            binding.playlistName.text = playlist.title
            
            // 곡 개수 설정
            binding.songCount.text = "${playlist.songCount}곡"
            
            // 클릭 리스너
            binding.root.setOnClickListener {
                onItemClick(playlist)
            }
        }
        
        /**
         * 플레이리스트 타입에 따른 아이콘 반환
         */
        private fun getIconForPlaylistType(type: String): Int {
            return when (type) {
                "최근 추가" -> android.R.drawable.ic_menu_recent_history
                "많이 재생" -> android.R.drawable.ic_menu_view
                "즐겨찾기" -> android.R.drawable.ic_input_add
                else -> android.R.drawable.ic_menu_add
            }
        }
    }

    /**
     * DiffUtil.ItemCallback 구현
     * - 효율적인 아이템 변경 감지
     */
    companion object {
        private class DiffCallback : DiffUtil.ItemCallback<Playlist>() {
            override fun areItemsTheSame(
                oldItem: Playlist,
                newItem: Playlist
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Playlist,
                newItem: Playlist
            ): Boolean = oldItem == newItem
        }
    }
}

