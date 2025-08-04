package com.flaao0.servicetest

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyJobService : JobService() {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob ")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                scope.launch {
                var workItem = params?.dequeueWork()
                while (workItem != null) {
                    val page = workItem.intent.getIntExtra(KEY_PAGE, 0)
                        for (i in 0 until 5) {
                            delay(1000)
                            log("Timer: $i $page")
                    }
                    params?.completeWork(workItem)
                    workItem = params?.dequeueWork()
                }
            }
        }
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        log("onStopJob ")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        log("onDestroy")
    }


    private fun log(message: String) {
        Log.d("SERVICE_TAG", message)
    }

    companion object {
        const val JOB_ID = 111
        const val KEY_PAGE = "page"

        fun newIntent(page: Int): Intent {
            return Intent().apply {
                putExtra(KEY_PAGE, page)
            }
        }
    }
}