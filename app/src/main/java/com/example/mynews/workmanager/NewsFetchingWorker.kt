package com.example.mynews.workmanager

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.mynews.api.ApiCaller
import com.example.mynews.utils.FQ
import com.example.mynews.utils.NEWS
import com.example.mynews.utils.QUERY
import io.reactivex.Single
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.mynews.R
import com.example.mynews.data.Doc


class NewsFetchingWorker(context: Context,
                         workerParameters: WorkerParameters): RxWorker(context, workerParameters) {

    val apiCaller = ApiCaller()

    override fun createWork(): Single<Result> {
        val query = inputData.getString(QUERY)!!
        val fq = inputData.getStringArray(FQ)
        return apiCaller.getNotified(query, fq as List<String>)
            .map {
                val outputData= workDataOf(NEWS to it.response.docs)
                showNotification(it.response.docs)
                Result.success(outputData)
            }
    }

    private fun showNotification(docs: List<Doc>) {

        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val channelId = "task_channel"
        val channelName = "task_name"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        for(doc in docs){
            val builder = NotificationCompat.Builder(applicationContext, channelId)
                .setContentTitle("En ce moment sur MyNews")
                .setContentText("${doc.abstract}")
                .setSmallIcon(R.mipmap.ic_splash_screen)

                        manager.notify(1, builder.build())
        }


    }

}