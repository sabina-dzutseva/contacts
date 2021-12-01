package com.gmail.dzutsevasabina.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.CheckBox
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.dzutsevasabina.R
import com.gmail.dzutsevasabina.repository.ContactRepositoryImpl
import com.gmail.dzutsevasabina.model.DetailedContact
import com.gmail.dzutsevasabina.view.CONTACT_ID
import com.gmail.dzutsevasabina.view.MESSAGE
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class ContactDetailsViewModel : ViewModel() {
    val liveData = MutableLiveData<DetailedContact>()
    private val contactRepository = ContactRepositoryImpl()
    private val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()


    fun getContactDetail(id: String, context: Context) {
        executor.execute { liveData.postValue(contactRepository.getContact(id, context)) }
    }

    fun handleAlarm(context: Context?, id: String?, button: Button) {
        val contact = liveData.value ?: return

        val alarmManager: AlarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, BirthdayAlertReceiver::class.java)
        intent.putExtra(CONTACT_ID, id)

        val message = String.format(context.resources.getString(R.string.notification_text, contact.name))
        intent.putExtra(MESSAGE, message)

        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (button is CheckBox) {
            if (button.isChecked) {
                contact.sendBirthdayNotifications = true

                val date = SimpleDateFormat("MM.dd", Locale.US).parse(contact.birthday)
                val calendar = GregorianCalendar.getInstance()
                if (date != null) {
                    calendar.time = date
                }

                val notifyTime: Calendar = GregorianCalendar.getInstance()

                notifyTime.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
                notifyTime.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))

                val offset = countOffset(notifyTime)

                notifyTime.add(Calendar.YEAR, offset)
                alarmManager.set(AlarmManager.RTC, notifyTime.timeInMillis, pendingIntent)
            } else {
                contact.sendBirthdayNotifications = false
                alarmManager.cancel(pendingIntent)
            }
        }
    }

    private fun countOffset(notifyTime: Calendar): Int {
        val year = notifyTime.get(Calendar.YEAR)
        val isSpecialDate =
            (notifyTime.get(Calendar.DAY_OF_MONTH) == 29)
                    && (notifyTime.get(Calendar.MONTH) == Calendar.FEBRUARY)

        var offset = 0

        if (isSpecialDate) {
            if ((year + 1) % 4 == 0) {
                offset = 1
            }

            if ((year + 2) % 4 == 0) {
                offset = 2
            }

            if ((year + 3) % 4 == 0) {
                offset = 3
            }
        }

        if (notifyTime.timeInMillis < System.currentTimeMillis()) {
            offset++
        }

        return offset
    }
}
