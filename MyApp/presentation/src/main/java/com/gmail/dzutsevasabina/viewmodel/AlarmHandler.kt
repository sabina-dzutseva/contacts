package com.gmail.dzutsevasabina.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.CheckBox
import com.gmail.dzutsevasabina.R
import com.gmail.dzutsevasabina.interactor.NextBirthdayModel
import com.gmail.dzutsevasabina.model.DetailedContact
import com.gmail.dzutsevasabina.view.CONTACT_ID
import com.gmail.dzutsevasabina.view.MESSAGE

class AlarmHandler(private val context: Context) {

    fun handleAlarm(contact: DetailedContact, id: String?, button: Button) {
        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, BirthdayAlertReceiver::class.java)
        if (id != null) {
            intent.putExtra(CONTACT_ID, Integer.parseInt(id))
        }

        val message = String.format(context.resources.getString(R.string.notification_text, contact.name))
        intent.putExtra(MESSAGE, message)

        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (button is CheckBox) {
            if (button.isChecked) {
                contact.sendBirthdayNotifications = true
                val notifyTime = NextBirthdayModel().getNextBirthdayDate(contact.birthday)
                alarmManager.set(AlarmManager.RTC, notifyTime.timeInMillis, pendingIntent)
            } else {
                contact.sendBirthdayNotifications = false
                alarmManager.cancel(pendingIntent)
            }
        }
    }
}
