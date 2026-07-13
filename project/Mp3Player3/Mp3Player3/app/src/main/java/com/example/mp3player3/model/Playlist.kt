package com.example.mp3player3.model

/**
 * Playlist - 사용자가 만드는 플레이리스트 데이터 모델
 * - songs 목록을 포함합니다.
 * - songCount는 songs.size 기반으로 자동 계산합니다.
 */
data class Playlist(
    val id: Long,
    val title: String,
    val thumbnail: String = "",
    val type: String = "플레이리스트",
    val songs: List<Song> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
) {
    val songCount: Int
        get() = songs.size
}
