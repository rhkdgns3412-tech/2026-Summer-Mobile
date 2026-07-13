package com.example.mp3player

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabLayout)
        val bottomNavigationView = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigationView)
        val tabTitles = resources.getStringArray(R.array.music_tabs).toList()

        viewPager.adapter = MusicPagerAdapter(this, tabTitles)
        viewPager.offscreenPageLimit = 1

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        bottomNavigationView.selectedItemId = R.id.nav_my_music
        bottomNavigationView.setOnItemSelectedListener { true }
    }

    private class MusicPagerAdapter(
        activity: FragmentActivity,
        private val tabTitles: List<String>
    ) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = tabTitles.size

        override fun createFragment(position: Int): Fragment {
            return MusicPageFragment.newInstance(tabTitles[position])
        }
    }

    class MusicPageFragment : Fragment() {

        override fun onCreateView(
            inflater: android.view.LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val context = requireContext()

            val root = FrameLayout(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(context.getColor(R.color.music_background))
            }

            val content = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setPadding(32, 32, 32, 32)
            }

            val title = TextView(context).apply {
                text = requireArguments().getString(ARG_TITLE).orEmpty()
                setTextColor(context.getColor(R.color.music_text_primary))
                textSize = 24f
                setPadding(0, 0, 0, 12)
            }

            val subtitle = TextView(context).apply {
                text = "탭 콘텐츠를 여기에 연결하세요"
                setTextColor(context.getColor(R.color.music_text_secondary))
                textSize = 15f
                gravity = Gravity.CENTER
            }

            content.addView(title)
            content.addView(subtitle)
            root.addView(content)
            return root
        }

        companion object {
            private const val ARG_TITLE = "arg_title"

            fun newInstance(title: String): MusicPageFragment {
                return MusicPageFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_TITLE, title)
                    }
                }
            }
        }
    }
}