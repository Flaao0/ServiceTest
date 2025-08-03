package com.flaao0.servicetest

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService: Service() {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        log("onStartCommand ")
        val start = intent?.getIntExtra(EXTRA_START, 0) ?: 0
        scope.launch {
            for (i in start..start + 10) {
                delay(1000)
                log(i.toString())
            }
        }
        return START_REDELIVER_INTENT

    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        log("onDestroy")
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", message)
    }

    companion object {

        private const val EXTRA_START = "start"
        fun newIntent(context: Context, number: Int): Intent {
            return Intent(context, MyService::class.java).apply {
                putExtra(EXTRA_START, number)
            }
        }
    }
}