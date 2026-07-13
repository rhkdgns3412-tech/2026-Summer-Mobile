package com.example.mp3player3.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mp3player3.R
import com.example.mp3player3.model.Song

/**
 * SongAdapter - RecyclerView 어댑터
 * - 곡 목록 표시
 * - 현재 재생 중인 곡 표시
 * - 곡 클릭 시 재생 기능
 */
class SongAdapter(
    private val onSongClick: (Song) -> Unit = {},
    private val currentPlayingSongId: Long = -1L
) : ListAdapter<Song, SongAdapter.SongViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view, onSongClick)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = getItem(position)
        holder.bind(song, song.id == currentPlayingSongId)
    }

    class SongViewHolder(
        itemView: View,
        private val onSongClick: (Song) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvArtist: TextView = itemView.findViewById(R.id.tvArtist)
        private val ivPlayingIndicator: ImageView = itemView.findViewById(R.id.ivPlayingIndicator)

        fun bind(song: Song, isPlaying: Boolean) {
            tvTitle.text = song.title
            tvArtist.text = song.artist

            // 현재 재생 중인 곡 표시
            if (isPlaying) {
                ivPlayingIndicator.visibility = View.VISIBLE
            } else {
                ivPlayingIndicator.visibility = View.GONE
            }

            // 곡 클릭 리스너
            itemView.setOnClickListener {
                onSongClick(song)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }
    }
}

