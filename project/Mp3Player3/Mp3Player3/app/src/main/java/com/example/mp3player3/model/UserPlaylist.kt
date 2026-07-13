package com.example.mp3player3.model

/**
 * UserPlaylist - 사용자가 생성한 플레이리스트
 *
 * @param id 플레이리스트 고유 ID
 * @param name 플레이리스트 이름
 * @param songCount 곡 개수
 * @param thumbnailUri 썸네일 이미지 URI (첫 곡의 이미지)
 * @param createdAt 생성 날짜
 */
data class UserPlaylist(
    val id: Long,
    val name: String,
    val songCount: Int,
    val thumbnailUri: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

