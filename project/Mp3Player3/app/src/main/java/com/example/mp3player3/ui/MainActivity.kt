package com.example.mp3player3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.mp3player3.R
import com.example.mp3player3.databinding.ActivityMainBinding
import com.example.mp3player3.ui.adapter.MainPagerAdapter
import com.example.mp3player3.ui.viewmodel.SharedMusicViewModel

/**
 * MainActivity - 호스트 액티비티
 * - MotionLayout 탭(5개)과 ViewPager2를 양방향으로 연동합니다.
 * - 페이지 구성: 좋아요, 플레이리스트, 곡, 앨범, 아티스트
 * - 탭 선택 시 ViewPager2 페이지가 변경되고, ViewPager2 스와이프 시 탭 상태가 업데이트됩니다.
 * - SharedMusicViewModel을 생성하여 전체 Fragment와 공유합니다. (MP3 플레이어 기능)
 * - Fragment 기반의 MVVM 패턴을 따릅니다.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainPagerAdapter
    private lateinit var sharedMusicViewModel: SharedMusicViewModel
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 시스템 바 inset 처리
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 공유 음악 플레이어 ViewModel 생성 (전체 Fragment에서 공유)
        sharedMusicViewModel = ViewModelProvider(this).get(SharedMusicViewModel::class.java)

        // ViewPager2 설정
        setupViewPager()

        // 탭 생성
        createTabs()

        // 메뉴 버튼 리스너 (향후 기능 확장용)
        binding.moreMenuButton.setOnClickListener {
            // TODO: 메뉴 구현
        }
    }

    private fun setupViewPager() {
        adapter = MainPagerAdapter(this)
        binding.viewPager.adapter = adapter

        // ViewPager2 페이지 변경 리스너 (스와이프 시 탭 동기화)
        // - ViewPager2가 스와이프로 페이지 변경 시 호출됩니다.
        // - 탭 인디케이터 위치와 탭 선택 상태를 업데이트합니다.
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position
                updateTabIndicator(position)     // 인디케이터 애니메이션
                updateTabSelection(position)     // 탭 텍스트 색상 업데이트
            }
        })
    }

    private fun createTabs() {
        // 탭 목록 (5개: 좋아요, 플레이리스트, 곡, 앨범, 아티스트)
        val tabCount = adapter.itemCount
        for (i in 0 until tabCount) {
            val tabView = LayoutInflater.from(this).inflate(R.layout.item_tab, binding.tabContainer, false) as LinearLayout
            val tabText = tabView.findViewById<TextView>(R.id.tabText)
            tabText.text = adapter.getTabTitle(i)

            // 탭 클릭 리스너: 탭을 클릭하면 ViewPager2의 해당 페이지로 이동
            val finalPosition = i
            tabView.setOnClickListener {
                binding.viewPager.setCurrentItem(finalPosition, true)
            }

            binding.tabContainer.addView(tabView)
        }

        // 초기 탭 선택 상태 설정
        updateTabSelection(0)
        updateTabIndicator(0)
    }

    private fun updateTabSelection(position: Int) {
        // 모든 탭의 텍스트 색상을 업데이트합니다.
        // - 선택된 탭(position): 흰색
        // - 비선택 탭: 회색
        for (i in 0 until binding.tabContainer.childCount) {
            val tabView = binding.tabContainer.getChildAt(i)
            val tabText = tabView.findViewById<TextView>(R.id.tabText)
            tabText.setTextColor(
                if (i == position) {
                    getColor(android.R.color.white)         // 선택된 탭: 흰색
                } else {
                    getColor(android.R.color.darker_gray)   // 비선택 탭: 회색
                }
            )
        }
    }

    private fun updateTabIndicator(position: Int) {
        // 탭 인디케이터(하단 바)를 선택된 탭 위치로 이동합니다.
        // - 300ms 애니메이션으로 부드러운 이동 효과를 제공합니다.
        val tabView = binding.tabContainer.getChildAt(position) ?: return
        val left = tabView.left + (tabView.width - 40) / 2
        binding.tabIndicator.animate()
            .translationX((left - binding.tabIndicator.left).toFloat())
            .duration = 300
    }
}

