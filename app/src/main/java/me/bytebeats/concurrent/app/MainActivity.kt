package me.bytebeats.concurrent.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java9.util.concurrent.CompletableFuture
import me.bytebeats.concurrent.withStarted

class MainActivity : AppCompatActivity() {
    private val start by lazy { findViewById<TextView>(R.id.start) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}