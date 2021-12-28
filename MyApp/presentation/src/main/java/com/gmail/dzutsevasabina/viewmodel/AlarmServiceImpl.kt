package com.gmail.dzutsevasabina.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.gmail.dzutsevasabina.R
import com.gmail.dzutsevasabina.interactor.AlarmService
import com.gmail.dzutsevasabina.view.CONTACT_ID
import com.gmail.dzutsevasabina.view.MESSAGE
import javax.inject.Inject

class AlarmServiceImpl @Inject constructor (private val context: Context) : AlarmService {
    private val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun setAlarm(time: Long, id: String?, name: String) {
        alarmManager.set(AlarmManager.RTC, time, createIntent(id, name))
    }

    override fun cancelAlarm(id: String?, name: String) {
        alarmManager.cancel(createIntent(id, name))
    }

    private fun createIntent(id: String?, name: String): PendingIntent {
        val intent = Intent(context, BirthdayAlertReceiver::class.java)
        if (id != null) {
            intent.putExtra(CONTACT_ID, Integer.parseInt(id))
        }

        val message = String.format(context.resources.getString(R.string.notification_text, name))
        intent.putExtra(MESSAGE, message)

        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}
