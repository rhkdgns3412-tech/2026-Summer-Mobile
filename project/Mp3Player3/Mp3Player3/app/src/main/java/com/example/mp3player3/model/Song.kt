package com.example.mp3player3.model

import java.io.Serializable

/**
 * Song 모델
 * - Serializable로 만들어 Intent extras로 곡 목록을 전달할 수 있습니다.
 */
data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val uri: String? = null
) : Serializable
