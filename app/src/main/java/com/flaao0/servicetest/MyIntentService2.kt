package com.flaao0.servicetest

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class MyIntentService2 : IntentService(SERVICE_NAME) {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        setIntentRedelivery(true)
    }


    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        scope.cancel()
    }

    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent ")
        val page = intent?.getIntExtra(KEY_PAGE, 0) ?: 0

        for (i in 0..15) {
            Thread.sleep(1000)
            log("Timer: $i $page")

        }
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", message)
    }



    companion object {
        private const val SERVICE_NAME = "MyIntentService"
        private const val KEY_PAGE = "page"

        fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyIntentService2::class.java).apply {
                putExtra(KEY_PAGE, page)
            }
        }
    }
}