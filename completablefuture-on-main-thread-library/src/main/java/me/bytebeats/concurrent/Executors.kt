package me.bytebeats.concurrent

import java.util.concurrent.Executor

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/9/7 11:44
 * @Version 1.0
 * @Description Executor Services
 */

class MainThreadExecutor() : Executor {
    override fun execute(command: Runnable?) {
        command?.let {
            MainHandler.instance().post(it)
        }
    }

    companion object Factory {
        private var INSTANCE: MainThreadExecutor? = null
        fun instance(): MainThreadExecutor {
            if (INSTANCE == null) {
                synchronized(MainThreadExecutor::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MainThreadExecutor()
                    }
                }
            }
            return INSTANCE!!
        }

        fun create(): MainThreadExecutor = MainThreadExecutor()
    }
}

