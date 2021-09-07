# Lifecycle-CompletableFuture

Android 主线程中使用 `CompletableFuture`. 并且与 `Android#Lifecycle` 结合起来使用.

Project Background
------

我司的一个小而美的项目中, 网络方式采用的是`RxJava` + `Retrofit 2` + `CompletableFuture`(`streamsupport`版本)方式, 并采用 `Kotlin` 语言完成.
<br>感觉这种方式挺不错的. 我所在工程因为年久失修, 未能采用诸多现代技术.
<br>但是采用了 `streamsupport`, 又因为 `streamsupport` 中的 `CompletableFuture` 既能像 `Future` 一样进行异步计算, 也能像 `Promise` 模式一样避免"回调地域(Callback
Hell)".
<br>唯一遗憾的是 `streamsupport#CompletableFuture` 默认跟 Android 主线程结合的不太好, 主要是 `CompletableFuture.whenComplete()`不能在主线程上回调, 因而才有了这个 idea 以及这个 idea 的实现即本工程.

Notes
------

- Java 8中的`CompletableFuture`目前为还不能直接在 Android 项目中使用.
  <br>这里的 `CompletableFuture` 来源于[streamsupport](https://github.com/streamsupport/streamsupport).
  <br>更具体地说, 来源于 [streamsupport:android-retrofuture](https://github.com/retrostreams/android-retrofuture).
- 由于`main-completablefuture`包里的依赖方式采用的是 `dependencies#implementation`,
  <br>所以依赖于 `main-completablefuture` 包的工程需要手动添加 [streamsupport:android-retrofuture](https://github.com/retrostreams/android-retrofuture) 的依赖.
  <br>例如: ```implementation 'net.sourceforge.streamsupport:android-retrofuture:1.7.3'```

Effect Graph
-------
<img src="/media/completablefuture_with_lifecycle.png"/>

How to Use?
------
<br>In Kotlin file:

```
        start.setOnClickListener {
            CompletableFuture.supplyAsync {
                try {
                    Thread.sleep(1000)

                    Log.i(TAG, Thread.currentThread().toString())
                } catch (e: InterruptedException) {
                    Log.i(TAG, e.message, e)
                }
            }.withStarted(this).whenComplete { t, u ->
                Log.i(TAG, Thread.currentThread().toString())
                if (u != null) {
                    Log.i(TAG, "Throwable", u)
                } else {
                    Log.i(TAG, t?.toString() ?: "null")
                }
            }
        }
```
