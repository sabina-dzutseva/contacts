package com.gmail.dzutsevasabina.interactor

import java.util.*

interface NextBirthdayInteractor {
    fun getNextBirthdayDate(birthday: String, currentDate: Calendar): Calendar
}
