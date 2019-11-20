package com.example.mynews.services

import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mynews.R
import com.example.mynews.repository.api.ApiCaller
import com.example.mynews.repository.data.NotificationServerResponse
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class FirebaseNotificationService : FirebaseMessagingService() {

    val apiCaller = ApiCaller()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("Remote message", "From: ${remoteMessage?.from}")
        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d("Remote message", "Message Notification Body: ${it.body}")
            //Message Services handle notification
            val notification = NotificationCompat.Builder(applicationContext)
                .setContentTitle(remoteMessage.from)
                .setContentText(it.body)
                .setSmallIcon(R.mipmap.ic_splash_screen)
                .build()
            val manager = NotificationManagerCompat.from(applicationContext)
            manager.notify(/*notification id*/0, notification)

        }
    }

    override fun onNewToken(token: String) {
        //handle token
        Log.d("Token", "$token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        apiCaller.sendTokenToServer(token)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                {
                    Log.i("Success", "Post success ${it.code}")
                },
                {
                    Log.i("Success", "Post error ${it.message}")
                }
            )


    }

}