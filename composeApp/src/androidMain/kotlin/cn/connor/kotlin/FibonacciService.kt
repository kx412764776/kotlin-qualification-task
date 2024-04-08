package cn.connor.kotlin

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class FibonacciService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val n = intent?.getIntExtra("N", -1) ?: -1
        if (n >= 0) {
            val sequence = generateSequence(0 to 1) { it.second to it.first + it.second }
                .map { it.first }
                .take(n)
                .joinToString(" ")
            Log.i("Fibonacci", sequence)
        }
        return START_NOT_STICKY
    }
}