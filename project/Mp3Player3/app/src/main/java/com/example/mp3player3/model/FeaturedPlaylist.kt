package com.example.mp3player3.model

/**
 * FeaturedPlaylist - 추천/기본 플레이리스트
 * - 최근에 추가한 곡, 많이 재생한 곡, 즐겨찾기 등 시스템 플레이리스트
 *
 * @param id 플레이리스트 고유 ID
 * @param name 플레이리스트 이름
 * @param songCount 곡 개수
 * @param iconResId 아이콘 리소스 ID
 */
data class FeaturedPlaylist(
    val id: Long,
    val name: String,
    val songCount: Int,
    val iconResId: Int
)

