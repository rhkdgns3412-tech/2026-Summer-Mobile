package com.example.mp3player3.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mp3player3.ui.fragments.AlbumFragment
import com.example.mp3player3.ui.fragments.ArtistFragment
import com.example.mp3player3.ui.fragments.FavoriteFragment
import com.example.mp3player3.ui.fragments.PlaylistFragment
import com.example.mp3player3.ui.fragments.SongFragment

/**
 * FragmentStateAdapter (ViewPager2용 어댑터)
 * - 5개의 Fragment 페이지를 관리합니다:
 *   0: FavoriteFragment (좋아요)
 *   1: PlaylistFragment (플레이리스트)
 *   2: SongFragment (곡)
 *   3: AlbumFragment (앨범)
 *   4: ArtistFragment (아티스트)
 * - ViewPager2의 페이지 변경 시 자동으로 Fragment 생성/관리합니다.
 * - MotionLayout의 탭과 양방향으로 연동됩니다.
 */
class MainPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    // 탭 타이틀 목록 (5개)
    private val tabs = listOf("좋아요", "플레이리스트", "곡", "앨범", "아티스트")

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteFragment()      // 좋아요
            1 -> PlaylistFragment()      // 플레이리스트
            2 -> SongFragment()          // 곡
            3 -> AlbumFragment()         // 앨범
            4 -> ArtistFragment()        // 아티스트
            else -> FavoriteFragment()
        }
    }

    /**
     * 탭 위치에 해당하는 타이틀을 반환합니다.
     * @param position 탭 위치 (0~4)
     * @return 탭 타이틀 문자열
     */
    fun getTabTitle(position: Int): String = tabs.getOrNull(position) ?: ""
}

