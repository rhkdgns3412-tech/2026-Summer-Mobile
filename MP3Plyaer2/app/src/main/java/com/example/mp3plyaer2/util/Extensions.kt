package com.example.mp3plyaer2.util

import android.util.Log

fun Any.logd(message: String) {
    Log.d(this::class.java.simpleName, message)
}

