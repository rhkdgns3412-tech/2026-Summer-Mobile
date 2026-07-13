package com.example.mp3player3.repository

import android.content.Context
import com.example.mp3player3.R
import com.example.mp3player3.model.Song

/**
 * MusicRepository
 * - 간단한 샘플 저장소: res/raw 에 있는 리소스를 스캔하여 Song 목록을 만듭니다.
 * - 실제 앱에서는 미디어 스토어나 서버/API 등을 사용하세요.
 */
class MusicRepository {
    fun getAllSongs(context: Context): List<Song> {
        val list = mutableListOf<Song>()
        return try {
            // R.raw 클래스의 모든 필드(리소스)를 순회합니다.
            val rawClass = Class.forName("${context.packageName}.R\$raw")
            val fields = rawClass.declaredFields
            var idCounter = 1L

            for (field in fields) {
                try {
                    field.isAccessible = true
                    val resId = field.getInt(null)
                    val fileName = field.name // raw 파일명
                    // 파일명에서 언더스코어를 공백으로 변환하여 사용자 친화적 제목 생성
                    val title = fileName.replace("_", " ").replaceFirstChar { it.uppercase() }
                    list.add(
                        Song(
                            id = idCounter++,
                            title = title,
                            artist = "Unknown",
                            uri = "android.resource://${context.packageName}/$resId"
                        )
                    )
                } catch (e: Exception) {
                    // 정수가 아닌 필드는 무시
                }
            }
            list.sortedBy { it.title }
        } catch (e: Exception) {
            // R.raw 클래스를 찾지 못한 경우 빈 목록 반환
            emptyList()
        }
    }
}

