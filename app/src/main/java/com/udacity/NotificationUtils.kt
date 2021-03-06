package com.udacity

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0
fun NotificationManager.sendNotification(messageBody:String,applicationContext: Context){

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_title)
    )
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)

        notify(NOTIFICATION_ID, builder.build())

}