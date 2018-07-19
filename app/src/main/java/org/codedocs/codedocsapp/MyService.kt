package org.codedocs.codedocsapp

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class fcm : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

    var notificationTitle: String? = null
    var notificationBody: String? = null

    // Check if message contains a data payload.

    notificationTitle = remoteMessage.getData().get("title");
    notificationBody = remoteMessage.getData().get("body")
    Log.e("bleh", notificationTitle)
    Log.e("bleh", notificationBody)

    sendNotification(notificationTitle!!, notificationBody!!);
}

private fun sendNotification(notificationTitle: String, notificationBody: String) {
    val intent = Intent(this, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT)

    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val notificationBuilder = NotificationCompat.Builder(this)
            .setAutoCancel(true)   //Automatically delete the notification
            .setSmallIcon(R.mipmap.ic_launcher) //Notification icon
            .setContentIntent(pendingIntent)
            .setContentTitle(notificationTitle)
            .setContentText(notificationBody)
            .setSound(defaultSoundUri) as NotificationCompat.Builder


    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    notificationManager.notify(0, notificationBuilder.build())
}
}
