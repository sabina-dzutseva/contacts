package com.gmail.dzutsevasabina.viewmodel

import com.gmail.dzutsevasabina.interactor.NextBirthdayModel
import com.gmail.dzutsevasabina.model.DetailedContact
import java.util.*

class AlarmHandler(private val alarmServiceImpl: AlarmServiceImpl) {

    fun handleAlarm(contact: DetailedContact, id: String?, isChecked: Boolean, currentDate: Calendar) {
        if (isChecked) {
            contact.sendBirthdayNotifications = true
            val notifyTime = NextBirthdayModel().getNextBirthdayDate(contact.birthday, currentDate)
            alarmServiceImpl.setAlarm(notifyTime.timeInMillis, id, contact.name)
        } else {
            contact.sendBirthdayNotifications = false
            alarmServiceImpl.cancelAlarm(id, contact.name)
        }
    }
}
