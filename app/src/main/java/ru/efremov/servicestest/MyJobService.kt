package ru.efremov.servicestest

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyJobService: JobService() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        log("onStartCommand")
        coroutineScope.launch {
            for (i in 0..100) {
                delay(1000)
                log("timer: $i")
            }
            jobFinished(p0, true)
        }
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d("onStopJob", p0.toString())
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyJobService: $message")
    }

    companion object {

        const val JOB_ID = 111
    }
}