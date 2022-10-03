package com.jcd.telusnavigation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.jcd.telusnavigation.first_flow.FirstFlowActivity

const val TELUS_NOTIFICATION_CHANNEL_ID = "telus"
const val TELUS_NOTIFICATION_CHANNEL_NAME = "telus name"
const val TELUS_NOTIFICATION_CHANNEL_DESCRIPTION = "telus description"

private fun Context.createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(
            TELUS_NOTIFICATION_CHANNEL_ID,
            TELUS_NOTIFICATION_CHANNEL_NAME,
            importance
        ).apply {
            description = TELUS_NOTIFICATION_CHANNEL_DESCRIPTION
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun Context.showFirstFlowNotificationImplicit() {
    createNotificationChannel()
    val pendingIntent = NavDeepLinkBuilder(this)
        .setGraph(R.navigation.first_flow_graph)
        .setDestination(R.id.firstFlowDFragment)
        .setComponentName(FirstFlowActivity::class.java)
        .setArguments(bundleOf("from_notification" to true))
        .createPendingIntent()

    var builder = NotificationCompat.Builder(this, TELUS_NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_happy_face)
        .setContentTitle("Hello there!")
        .setContentText("Open this notification to go to first flow's C fragment")
        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(false)

    with(NotificationManagerCompat.from(this)) {
        notify(1234, builder.build())
    }
}

fun Context.showFirstFlowNotificationExplicit() {
    createNotificationChannel()
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = "durini://telusnavigation.com/firstFlow/true".toUri()
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }

    val pendingIntent = PendingIntent.getActivity(
        this,
        1,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    var builder = NotificationCompat.Builder(this, TELUS_NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_forward)
        .setContentTitle("Hello there!")
        .setContentText("Open this notification to go to first flow's C fragment using explicit deeplink")
        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(false)

    with(NotificationManagerCompat.from(this)) {
        notify(12345, builder.build())
    }
}