package me.bytebeats.concurrent

import android.os.Handler
import android.os.Looper

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/9/7 11:47
 * @Version 1.0
 * @Description Handler that runs on main thread.
 */

class MainHandler : Handler(Looper.getMainLooper()) {
    companion object Factory {
        private var INSTANCE: MainHandler? = null
        fun instance(): MainHandler {
            if (INSTANCE == null) {
                synchronized(MainHandler::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MainHandler()
                    }
                }
            }
            return INSTANCE!!
        }

        fun create(): MainHandler = MainHandler()
    }
}