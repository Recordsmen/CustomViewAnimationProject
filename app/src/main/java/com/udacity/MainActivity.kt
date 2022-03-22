package com.udacity

import android.animation.ObjectAnimator
import android.app.DownloadManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action
    private lateinit var request: DownloadManager.Request

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        custom_button.setOnClickListener {
            request =
                DownloadManager.Request(Uri.parse(URL))
                    .setTitle(getString(R.string.app_name))
                    .setDescription(getString(R.string.app_description))
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)
            download()
        }

        custom_button.setOnClickListener {
            Log.i(TAG,"buttonIsClicked")
            notificationManager.sendNotification(applicationContext.getString(R.string.notification_description),applicationContext)
        }
        notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        }
    }

    private fun download() {
        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID = downloadManager.enqueue(request)// enqueue puts the download request in the queue.
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.glide ->
                    if (checked) {
                        custom_button.isClickable = true
                        request =
                            DownloadManager.Request(Uri.parse(URL))
                                .setTitle(getString(R.string.app_name))
                                .setDescription(getString(R.string.app_description))
                                .setRequiresCharging(false)
                                .setAllowedOverMetered(true)
                                .setAllowedOverRoaming(true)
                    }
                R.id.project ->
                    if (checked) {
                        custom_button.isClickable = true
                        request =
                            DownloadManager.Request(Uri.parse(URL2))
                                .setTitle(getString(R.string.app_name))
                                .setDescription(getString(R.string.app_description))
                                .setRequiresCharging(false)
                                .setAllowedOverMetered(true)
                                .setAllowedOverRoaming(true)
                    }
                R.id.retrofit ->
                    if (checked) {
                        custom_button.isClickable = true
                        request =
                            DownloadManager.Request(Uri.parse(URL3))
                                .setTitle(getString(R.string.app_name))
                                .setDescription(getString(R.string.app_description))
                                .setRequiresCharging(false)
                                .setAllowedOverMetered(true)
                                .setAllowedOverRoaming(true)
                    }
                    else -> {
                        custom_button.isClickable = false
                        Toast.makeText(this,"Choose Download Resourse",Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }


    companion object {
        private const val URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val URL2 = "https://github.com/square/retrofit"
        private const val URL3 = "https://github.com/bumptech/glide"
        private const val CHANNEL_ID = "channelId"
    }

}
