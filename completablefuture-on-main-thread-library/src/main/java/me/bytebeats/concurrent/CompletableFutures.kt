package me.bytebeats.concurrent

import java9.util.concurrent.CompletableFuture
import java9.util.concurrent.CompletionStage
import java9.util.function.Function


/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/9/7 11:47
 * @Version 1.0
 * @Description CompletableFuture extension functions.
 */

fun <T, U> CompletableFuture<T>.thenComposeOnMainThread(fn: Function<T, CompletionStage<U>>?): CompletableFuture<U> {
    return this.thenComposeAsync(fn, MainThreadExecutor.Factory.create())
}