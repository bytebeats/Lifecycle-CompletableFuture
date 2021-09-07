package me.bytebeats.concurrent

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.lang.ref.WeakReference
import java.util.concurrent.Executor

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/9/7 11:44
 * @Version 1.0
 * @Description Executor Services
 */

class MainExecutor() : Executor {
    override fun execute(command: Runnable?) {
        command?.let {
            MainHandler.instance().post(it)
        }
    }

    companion object Factory {
        private var INSTANCE: MainExecutor? = null
        fun instance(): MainExecutor {
            if (INSTANCE == null) {
                synchronized(MainExecutor::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MainExecutor()
                    }
                }
            }
            return INSTANCE!!
        }

        fun create(): MainExecutor = MainExecutor()
    }
}

abstract class ActiveLifecycleMainExecutor(
    lifecycle: Lifecycle,
    private val blockWhenLifecycleInactive: (() -> Unit)? = null
) : Executor, LifecycleObserver {
    protected val weaklyReachableLifecycle = WeakReference(lifecycle)
    private var isCommandExecuted = false

    init {
        MainHandler.instance().post {
            if (isLifecycleActive()) {
                weaklyReachableLifecycle.get()?.addObserver(this)
            } else {
                whenLifecycleInactive(true)
            }
        }
    }

    abstract fun isLifecycleActive(): Boolean

    protected open fun onLifecycleInactive() {
        whenLifecycleInactive(true)
    }

    private fun whenLifecycleInactive(isLifecycleInactive: Boolean) {
        if (isCommandExecuted) return
        weaklyReachableLifecycle.get()?.removeObserver(this)
        isCommandExecuted = true
        if (isLifecycleInactive) {
            blockWhenLifecycleInactive?.invoke()
        }
    }


    override fun execute(command: Runnable?) {
        if (isCommandExecuted || command == null) return
        MainHandler.instance().post {
            if (isCommandExecuted) return@post
            if (isLifecycleActive()) {
                command.run()
                whenLifecycleInactive(false)
            } else {
                whenLifecycleInactive(true)
            }
        }
    }
}

class AtLeastStartedMainExecutor(
    lifecycle: Lifecycle,
    blockWhenLifecycleInactive: (() -> Unit)? = null
) : ActiveLifecycleMainExecutor(lifecycle, blockWhenLifecycleInactive) {
    override fun isLifecycleActive(): Boolean =
        weaklyReachableLifecycle.get()?.currentState?.isAtLeast(Lifecycle.State.STARTED) == true

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLifecycleInactive() {
        super.onLifecycleInactive()
    }
}

class AtLeastResumedMainExecutor(
    lifecycle: Lifecycle,
    blockWhenLifecycleInactive: (() -> Unit)? = null
) : ActiveLifecycleMainExecutor(lifecycle, blockWhenLifecycleInactive) {
    override fun isLifecycleActive(): Boolean =
        weaklyReachableLifecycle.get()?.currentState?.isAtLeast(Lifecycle.State.RESUMED) == true

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onLifecycleInactive() {
        super.onLifecycleInactive()
    }
}

