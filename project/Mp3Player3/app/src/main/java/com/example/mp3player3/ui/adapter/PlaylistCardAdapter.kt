package com.example.mp3player3.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mp3player3.R
import com.example.mp3player3.model.Playlist
import com.google.android.material.card.MaterialCardView

/**
 * PlaylistCardAdapter
 * - 사용자가 만든 플레이리스트를 카드 형태로 보여줍니다.
 * - 선택된 플레이리스트는 강조 표시됩니다.
 */
class PlaylistCardAdapter(
    private val onItemClick: (Playlist) -> Unit = {}
) : ListAdapter<Playlist, PlaylistCardAdapter.ViewHolder>(DiffCallback()) {

    var selectedPlaylistId: Long = -1L
        set(value) {
            field = value
            submitList(currentList.toList())
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist_card, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), getItem(position).id == selectedPlaylistId)
    }

    class ViewHolder(
        itemView: View,
        private val onItemClick: (Playlist) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val playlistCard: MaterialCardView = itemView.findViewById(R.id.playlistCard)
        private val playlistThumbnail: View = itemView.findViewById(R.id.playlistThumbnail)
        private val playlistTitle: TextView = itemView.findViewById(R.id.playlistTitle)
        private val playlistSongCount: TextView = itemView.findViewById(R.id.playlistSongCount)

        fun bind(playlist: Playlist, selected: Boolean) {
            playlistTitle.text = playlist.title
            playlistSongCount.text = "${playlist.songCount}곡"
            playlistThumbnail.setBackgroundColor(
                ContextCompat.getColor(itemView.context, if (selected) android.R.color.holo_blue_dark else android.R.color.darker_gray)
            )
            playlistCard.strokeWidth = if (selected) 4 else 1
            playlistCard.strokeColor = ContextCompat.getColor(itemView.context, if (selected) android.R.color.holo_blue_light else android.R.color.darker_gray)
            itemView.setOnClickListener { onItemClick(playlist) }
        }
    }

    companion object {
        private class DiffCallback : DiffUtil.ItemCallback<Playlist>() {
            override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean = oldItem == newItem
        }
    }
}
