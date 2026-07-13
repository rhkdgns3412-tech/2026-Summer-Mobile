package com.example.mp3plyaer2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mp3plyaer2.databinding.ActivityMainBinding
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.mp3plyaer2.ui.fragments.AlbumFragment
import com.example.mp3plyaer2.ui.fragments.ArtistFragment
import com.example.mp3plyaer2.ui.fragments.LikeFragment
import com.example.mp3plyaer2.ui.fragments.PlaylistFragment
import com.example.mp3plyaer2.ui.fragments.SongFragment

/**
 * MainActivity: single Activity hosting multiple Fragments.
 * - Uses ViewBinding
 * - Uses Material components (defined in themes)
 * - Handles fragment switching manually (no Navigation Component)
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup ViewPager2 with FragmentStateAdapter (5 pages: Like, Playlist, Songs, Albums, Artists)
        val pagerAdapter = object : FragmentStateAdapter(this as FragmentActivity) {
            override fun getItemCount(): Int = 5
            override fun createFragment(position: Int) = when (position) {
                0 -> LikeFragment.newInstance()
                1 -> PlaylistFragment.newInstance()
                2 -> SongFragment.newInstance()
                3 -> AlbumFragment.newInstance()
                4 -> ArtistFragment.newInstance()
                else -> PlaylistFragment.newInstance()
            }
        }

        binding.viewPager.adapter = pagerAdapter

        // Map tab views to MotionScene states
        val tabIds = listOf(
            R.id.tab_like,
            R.id.tab_playlist,
            R.id.tab_songs,
            R.id.tab_albums,
            R.id.tab_artists
        )

        val stateIds = listOf(
            R.id.state_tab_0,
            R.id.state_tab_1,
            R.id.state_tab_2,
            R.id.state_tab_3,
            R.id.state_tab_4
        )

        // Tab click -> change page (smooth) and animate MotionLayout
        tabIds.forEachIndexed { index, id ->
            binding.root.findViewById<View>(id).setOnClickListener {
                binding.viewPager.setCurrentItem(index, true)
                binding.motionTabs.transitionToState(stateIds[index])
            }
        }

        // Sync MotionLayout with ViewPager2 during scroll
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                // if swiping between adjacent pages, set the corresponding transition and progress
                if (positionOffset > 0f && position < stateIds.size - 1) {
                    val startState = stateIds[position]
                    val endState = stateIds[position + 1]
                    binding.motionTabs.setTransition(startState, endState)
                    binding.motionTabs.progress = positionOffset
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Ensure final state after settling
                binding.motionTabs.transitionToState(stateIds[position])
            }
        })
    }
}
