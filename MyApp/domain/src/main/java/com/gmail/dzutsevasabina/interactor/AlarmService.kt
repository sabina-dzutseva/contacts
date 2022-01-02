package com.gmail.dzutsevasabina.interactor

interface AlarmService {
    fun setAlarm(time: Long, id: String?, name: String)
    fun cancelAlarm(id: String?, name: String)
}
