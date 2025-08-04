package com.flaao0.servicetest

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class MyJobIntentService : JobIntentService() {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }


    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        scope.cancel()
    }

    override fun onHandleWork(intent: Intent) {
        log("onHandleIntent ")
        val page = intent.getIntExtra(KEY_PAGE, 0)

        for (i in 0..15) {
            Thread.sleep(1000)
            log("Timer: $i $page")

        }
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", message)
    }



    companion object {
        private const val KEY_PAGE = "page"
        private const val JOB_ID = 123

        fun enqueue(context: Context, page: Int) {
            enqueueWork(
                context,
                MyJobIntentService::class.java,
                JOB_ID,
                newIntent(context, page)
            )
        }

        private fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyJobIntentService::class.java).apply {
                putExtra(KEY_PAGE, page)
            }
        }
    }
}