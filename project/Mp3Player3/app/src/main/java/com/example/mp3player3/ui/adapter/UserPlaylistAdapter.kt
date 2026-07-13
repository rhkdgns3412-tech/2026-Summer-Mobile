package com.example.mp3player3.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mp3player3.databinding.ItemUserPlaylistBinding
import com.example.mp3player3.model.Playlist
import kotlin.random.Random

/**
 * UserPlaylistAdapter
 * - 세로 RecyclerView용 어댑터
 * - 사용자가 만든 플레이리스트 목록 표시
 * - 왼쪽: 썸네일(정사각형), 오른쪽: 제목 및 곡 개수
 * - Divider 포함 (마지막 아이템 제외)
 */
class UserPlaylistAdapter(
    private val onItemClick: (Playlist) -> Unit = {}
) : ListAdapter<Playlist, UserPlaylistAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserPlaylistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position == itemCount - 1)
    }

    /**
     * ViewHolder - 플레이리스트 항목을 표시합니다.
     * - 왼쪽: 정사각형 썸네일 (색상 무작위)
     * - 오른쪽: 플레이리스트 제목 및 곡 개수
     */
    inner class ViewHolder(
        private val binding: ItemUserPlaylistBinding,
        private val onItemClick: (Playlist) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(playlist: Playlist, isLastItem: Boolean) {
            // 썸네일 설정 (썸네일 URI가 없으면 무작위 색상 배경)
            if (playlist.thumbnail.isEmpty()) {
                // 무작위 색상 생성 (같은 플레이리스트에 대해 일관된 색상 제공)
                val color = generateColorForPlaylist(playlist.id)
                binding.playlistThumbnail.setBackgroundColor(color)
            } else {
                // 실제로는 여기서 Glide 등을 사용하여 이미지 로드
                // binding.playlistThumbnail.load(playlist.thumbnail)
                val color = generateColorForPlaylist(playlist.id)
                binding.playlistThumbnail.setBackgroundColor(color)
            }

            // 플레이리스트 제목
            binding.playlistTitle.text = playlist.title

            // 곡 개수
            binding.playlistSongCount.text = "${playlist.songCount}곡"

            // Divider 설정 (마지막 아이템 제외)
            binding.divider.visibility = if (isLastItem) {
                android.view.View.GONE
            } else {
                android.view.View.VISIBLE
            }

            // 클릭 리스너
            binding.root.setOnClickListener {
                onItemClick(playlist)
            }
        }

        /**
         * 플레이리스트 ID를 기반으로 일관된 색상을 생성합니다.
         */
        private fun generateColorForPlaylist(playlistId: Long): Int {
            val random = Random(playlistId)
            val hue = random.nextFloat() * 360
            val saturation = 0.6f
            val value = 0.85f
            return android.graphics.Color.HSVToColor(floatArrayOf(hue, saturation, value))
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

