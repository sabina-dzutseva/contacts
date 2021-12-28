package com.gmail.dzutsevasabina.viewmodel

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.gmail.dzutsevasabina.R
import com.gmail.dzutsevasabina.view.CONTACT_DETAIL_ID
import com.gmail.dzutsevasabina.view.CONTACT_ID
import com.gmail.dzutsevasabina.view.FRAGMENT_ID
import com.gmail.dzutsevasabina.view.MESSAGE
import com.gmail.dzutsevasabina.view.activity.MainActivity
import java.util.*

class BirthdayAlertReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        createNotification(context, intent)

        val alarmManager: AlarmManager =
            context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar: Calendar = nextBirthday()

        if (intent != null) {
            val broadcastIntent =
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.set(AlarmManager.RTC, calendar.timeInMillis, broadcastIntent)
        }
    }

    private fun createNotification(context: Context?, intent: Intent?) {
        val notificationManager: NotificationManager =
            context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(context.getString(R.string.channel_id), name, importance)

            notificationManager.createNotificationChannel(channel)
        }

        val activityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val id = intent?.getIntExtra(CONTACT_ID, -1)
        activityIntent.putExtra(CONTACT_DETAIL_ID, id)
        activityIntent.putExtra(FRAGMENT_ID, R.layout.fragment_details)

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, context.getString(R.string.channel_id))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(intent?.getStringExtra(MESSAGE))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(0, builder.build())
    }

    private fun nextBirthday(): Calendar {
        val calendar: Calendar = GregorianCalendar.getInstance()

        if (calendar.get(Calendar.MONTH) == 2 && calendar.get(Calendar.DAY_OF_MONTH) == 29) {
            calendar.add(Calendar.YEAR, 4)
        } else {
            calendar.add(Calendar.YEAR, 1)
        }

        return calendar
    }
}
