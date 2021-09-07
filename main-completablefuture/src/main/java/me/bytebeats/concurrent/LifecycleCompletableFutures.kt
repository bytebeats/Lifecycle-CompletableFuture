package me.bytebeats.concurrent

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import java9.util.concurrent.CompletableFuture

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/9/7 16:06
 * @Version 1.0
 * @Description CompletableFuture works with Lifecycle.
 */

fun <T> CompletableFuture<T>.withStarted(lifecycle: Lifecycle): CompletableFuture<T> {
    val future = CompletableFuture<T>()
    this.whenCompleteAsync(
        { t, u -> if (u != null) future.completeExceptionally(u) else future.complete(t) },
        AtLeastStartedMainExecutor(lifecycle) {
            future.cancel(false)
        })
    return future
}

fun <T> CompletableFuture<T>.withResumed(lifecycle: Lifecycle): CompletableFuture<T> {
    val future = CompletableFuture<T>()
    this.whenCompleteAsync(
        { t, u -> if (u != null) future.completeExceptionally(u) else future.complete(t) },
        AtLeastResumedMainExecutor(lifecycle) {
            future.cancel(false)
        })
    return future
}

fun <T> Lifecycle.withStarted(cf: CompletableFuture<T>): CompletableFuture<T> {
    return cf.withStarted(this)
}

fun <T> Lifecycle.withResumed(cf: CompletableFuture<T>): CompletableFuture<T> {
    return cf.withResumed(this)
}

fun <T> ComponentActivity.withStarted(cf: CompletableFuture<T>): CompletableFuture<T> {
    return cf.withStarted(this.lifecycle)
}

fun <T> ComponentActivity.withResumed(cf: CompletableFuture<T>): CompletableFuture<T> {
    return cf.withResumed(this.lifecycle)
}

fun <T> Fragment.withStarted(cf: CompletableFuture<T>): CompletableFuture<T> {
    return cf.withStarted(this.viewLifecycleOwner.lifecycle)
}

fun <T> Fragment.withResumed(cf: CompletableFuture<T>): CompletableFuture<T> {
    return cf.withResumed(this.viewLifecycleOwner.lifecycle)
}

fun <T> CompletableFuture<T>.withStarted(ca: ComponentActivity): CompletableFuture<T> {
    return withStarted(ca.lifecycle)
}

fun <T> CompletableFuture<T>.withResumed(ca: ComponentActivity): CompletableFuture<T> {
    return withResumed(ca.lifecycle)
}

fun <T> CompletableFuture<T>.withStarted(f: Fragment): CompletableFuture<T> {
    return withStarted(f.viewLifecycleOwner.lifecycle)
}

fun <T> CompletableFuture<T>.withResumed(f: Fragment): CompletableFuture<T> {
    return withResumed(f.viewLifecycleOwner.lifecycle)
}
