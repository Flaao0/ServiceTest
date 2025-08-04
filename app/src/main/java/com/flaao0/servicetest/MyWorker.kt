package com.flaao0.servicetest

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf


class MyWorker(
    context: Context,
    private val workerParameters: WorkerParameters,
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        log("doWork ")
        val page = workerParameters.inputData.getInt(KEY_PAGE, 0)

        for (i in 0..15) {
            Thread.sleep(1000)
            log("Timer: $i $page")
        }

        return Result.success()
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", message)
    }

    companion object {
        private const val KEY_PAGE = "page"
        const val WORK_NAME = "work_name"


        fun makeRequest(page: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<MyWorker>()
                .setInputData(workDataOf(KEY_PAGE to page))
                .setConstraints(makeConstraints())
                .build()
        }

        private fun makeConstraints() : Constraints {
            return Constraints.Builder()
                .setRequiresCharging(true)
                .build()
        }

    }

}