package com.example.mp3player3.util

import android.content.Context

/**
 * 간단한 유틸 확장 함수 모음
 */
fun Context.dpToPx(dp: Int): Int = (dp * resources.displayMetrics.density).toInt()

