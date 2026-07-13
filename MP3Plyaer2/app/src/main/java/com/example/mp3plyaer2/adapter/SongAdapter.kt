package com.example.mp3plyaer2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mp3plyaer2.databinding.ItemSongBinding
import com.example.mp3plyaer2.model.Song

/** Simple RecyclerView adapter for displaying a list of songs. */
class SongAdapter(private val items: List<Song>) : RecyclerView.Adapter<SongAdapter.Holder>() {

    inner class Holder(private val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Song) {
            binding.songTitle.text = item.title
            binding.songArtist.text = item.artist
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

