package me.bytebeats.concurrent

import java9.util.concurrent.CompletableFuture
import java9.util.concurrent.CompletionStage
import java9.util.function.BiConsumer
import java9.util.function.BiFunction
import java9.util.function.Consumer
import java9.util.function.Function


/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/9/7 11:47
 * @Version 1.0
 * @Description CompletableFuture extension functions.
 */

fun <T, U> CompletableFuture<T>.thenApplyOnMainThread(fn: Function<in T?, out U?>?): CompletionStage<U?> {
    return this.thenApplyAsync(fn, MainThreadExecutor.instance())
}

fun <T> CompletableFuture<T>.thenAcceptOnMainThread(action: Consumer<in T?>?): CompletionStage<Void?> {
    return this.thenAcceptAsync(action, MainThreadExecutor.instance())
}

fun <T> CompletableFuture<T>.thenRunOnMainThread(action: Runnable?): CompletionStage<Void?> {
    return this.thenRunAsync(action, MainThreadExecutor.instance())
}

fun <T, U, V> CompletableFuture<T>.thenCombineOnMainThread(
    other: CompletionStage<out U?>?,
    fn: BiFunction<in T?, in U?, out V>?
): CompletionStage<V?> {
    return this.thenCombineAsync(other, fn, MainThreadExecutor.instance())
}

fun <T, U> CompletableFuture<T>.thenAcceptBothOnMainThread(
    other: CompletionStage<out U?>?,
    action: BiConsumer<in T?, in U?>?
): CompletionStage<Void?> {
    return this.thenAcceptBothAsync(other, action, MainThreadExecutor.instance())
}

fun <T> CompletableFuture<T>.runAfterBothOnMainThread(
    other: CompletionStage<*>?,
    action: Runnable?
): CompletionStage<Void?> {
    return this.runAfterBothAsync(other, action, MainThreadExecutor.instance())
}

fun <T, U> CompletableFuture<T>.applyToEitherOnMainThread(
    other: CompletionStage<out T>?,
    fn: Function<in T?, U>?
): CompletionStage<U?> {
    return this.applyToEitherAsync(other, fn, MainThreadExecutor.instance())
}

fun <T> CompletableFuture<T>.acceptEitherOnMainThread(
    other: CompletionStage<out T>?,
    action: Consumer<in T?>?
): CompletionStage<Void?> {
    return this.acceptEitherAsync(other, action, MainThreadExecutor.instance())
}

fun <T> CompletableFuture<T>.runAfterEitherOnMainThread(
    other: CompletionStage<*>?,
    action: Runnable?
): CompletionStage<Void?> {
    return this.runAfterEitherAsync(other, action, MainThreadExecutor.instance())
}

fun <T, U> CompletableFuture<T>.thenComposeOnMainThread(fn: Function<in T?, out CompletionStage<U>>?): CompletionStage<U?> {
    return this.thenComposeAsync(fn, MainThreadExecutor.instance())
}

fun <T, U> CompletableFuture<T>.handleOnMainThread(fn: BiFunction<in T?, Throwable?, out U?>?): CompletionStage<U?> {
    return this.handleAsync(fn, MainThreadExecutor.instance())
}

fun <T> CompletableFuture<T>.whenCompleteOnMainThread(action: BiConsumer<in T?, in Throwable?>?): CompletionStage<T?> {
    return this.whenCompleteAsync(action, MainThreadExecutor.instance())
}