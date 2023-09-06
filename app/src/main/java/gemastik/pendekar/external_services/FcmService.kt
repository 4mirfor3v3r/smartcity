package gemastik.pendekar.external_services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import gemastik.pendekar.CHANNEL_ID
import gemastik.pendekar.CHANNEL_NAME
import gemastik.pendekar.MainActivity
import gemastik.pendekar.utils.ApplicationContext

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FcmService : FirebaseMessagingService(){

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        showAlarmNotification(ApplicationContext.get(),remoteMessage.notification?.title?:"",remoteMessage.notification?.body?:"",System.currentTimeMillis().toInt())
        super.onMessageReceived(remoteMessage)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showAlarmNotification(context: Context, title: String, message: String, notifId: Int) {

        val notificationIntent = Intent(context, MainActivity::class.java)

        notificationIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP
                or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setSound(alarmSound)
            .setAutoCancel(true)

//        val i = Intent(context.applicationContext, MainActivity::class.java)
//        val contentIntent = PendingIntent.getActivity(
//            context.applicationContext, 0,
//            i, PendingIntent.FLAG_UPDATE_CURRENT
//        )
        /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        Materi ini akan dibahas lebih lanjut di modul extended
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(CHANNEL_ID)

            notificationManagerCompat.createNotificationChannel(channel)
        }
//        builder.setContentIntent(contentIntent)
        val notification = builder.build()

        notificationManagerCompat.notify(notifId, notification)

    }
}