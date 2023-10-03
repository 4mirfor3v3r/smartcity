package gemastik.pendekar

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.support.multidex.MultiDexApplication
import com.google.firebase.messaging.FirebaseMessaging
import gemastik.pendekar.utils.ApplicationContext

const val CHANNEL_ID = "PKMKC-SINAR-Schedule"
const val CHANNEL_NAME = "PKMKC-SINAR-Notification"

class PendekarApplication: MultiDexApplication() {

    @SuppressLint("NewApi")
    private val notificationChannel = listOf(
        NotificationChannel(CHANNEL_ID,"PKMKC-SINAR-Notification", NotificationManager.IMPORTANCE_HIGH)
    )
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        ApplicationContext.getInstance().init(applicationContext)
        FirebaseMessaging.getInstance().subscribeToTopic("pkmkc-danger-topic")
    }

    @SuppressLint("NewApi")
    private fun createNotificationChannel() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationChannel.forEach {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(
                    it.apply {
                        enableLights(true)
                        setShowBadge(true)
                        setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
                        lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                    }
                )
            }
        }
    }
}