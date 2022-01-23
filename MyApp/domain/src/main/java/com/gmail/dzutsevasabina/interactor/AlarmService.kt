package com.gmail.dzutsevasabina.interactor

interface AlarmService {
    fun setAlarm(time: Long, id: Int, name: String)
    fun cancelAlarm(id: Int, name: String)
}
