package com.example.mp3plyaer2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mp3plyaer2.ui.fragments.AlbumFragment
import com.example.mp3plyaer2.ui.fragments.ArtistFragment
import com.example.mp3plyaer2.ui.fragments.LikeFragment
import com.example.mp3plyaer2.ui.fragments.PlaylistFragment
import com.example.mp3plyaer2.ui.fragments.SongFragment

/**
 * FragmentStateAdapter for ViewPager2 that manages 5 pages:
 * 0 - Like
 * 1 - Playlist
 * 2 - Song
 * 3 - Album
 * 4 - Artist
 */
class MusicPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LikeFragment.newInstance()
            1 -> PlaylistFragment.newInstance()
            2 -> SongFragment.newInstance()
            3 -> AlbumFragment.newInstance()
            4 -> ArtistFragment.newInstance()
            else -> PlaylistFragment.newInstance()
        }
    }
}

