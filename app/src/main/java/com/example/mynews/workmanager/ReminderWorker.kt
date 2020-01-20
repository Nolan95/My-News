package com.example.mynews.workmanager

import android.content.Context
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.coroutineScope
import java.net.SocketException
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Sadate on 2020-01-20.
 */

class ReminderWorker(appContext: Context, workerParams: WorkerParameters): CoroutineWorker(appContext, workerParams) {

    companion object {
        private const val REMINDER_WORK_NAME = "reminder"
        private const val PARAM_NAME = "name" // optional - send parameter to worker
        // private const val RESULT_ID = "id"

        fun runAt() {
            val workManager = WorkManager.getInstance()


            val dat = Date()//initializes to now
            Log.d("Date", "$dat")

            val calAlarm = Calendar.getInstance()
            Log.d("calAlarm", "$calAlarm")

            val calNow = Calendar.getInstance()
            Log.d("calNow", "$calNow")

            calNow.time = dat
            Log.d("calNow with date", "$calNow")

            calAlarm.time = dat
            Log.d("calAlarm with date", "$calAlarm")

            calAlarm.set(Calendar.HOUR_OF_DAY, 11)//set the alarm time
            calAlarm.set(Calendar.MINUTE, 0)
            Log.d("calAlarm with set", "$calAlarm")

            if (calAlarm.before(calNow)) {//if its in the past increment
                calAlarm.add(Calendar.DATE, 1)
            }

            val duration =  calAlarm.timeInMillis - calNow.timeInMillis

            // optional constraints
            /*
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
             */

            // optional data
            val data = workDataOf(PARAM_NAME to "Timer 01")

            val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(duration, TimeUnit.MILLISECONDS)
                // .setConstraints(constraints)
                .setInputData(data) // optional
                .build()

            workManager.enqueueUniqueWork(REMINDER_WORK_NAME, ExistingWorkPolicy.REPLACE, workRequest)
        }

        fun cancel() {
            //Timber.d("cancel")
            val workManager = WorkManager.getInstance()
            workManager.cancelUniqueWork(REMINDER_WORK_NAME)
        }
    }


    override suspend fun doWork(): Result = coroutineScope {
        val worker = this@ReminderWorker
        val context = applicationContext

        val name = inputData.getString(PARAM_NAME)
        //Timber.d("doWork=$name")

        var isScheduleNext = true
        try {
            // do something

            // possible to return result
            // val data = workDataOf(RESULT_ID to 1)
            // Result.success(data)

            Result.success()
        }
        catch (e: Exception) {
            // only retry 3 times
            if (runAttemptCount > 3) {
                //Timber.d("runAttemptCount=$runAttemptCount, stop retry")
                return@coroutineScope Result.success()
            }

            // retry if network failure, else considered failed
            when(e.cause) {
                is SocketException -> {
                    //Timber.e(e.toString(), e.message)
                    isScheduleNext = false
                    Result.retry()
                }
                else -> {
                    //Timber.e(e)
                    Result.failure()
                }
            }
        }
        finally {
            // only schedule next day if not retry, else it will overwrite the retry attempt
            // - because we use uniqueName with ExistingWorkPolicy.REPLACE
            if (isScheduleNext) {
                runAt() // schedule for next day
            }
        }
    }

}