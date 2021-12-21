package com.gmail.dzutsevasabina.interactor

import java.text.SimpleDateFormat
import java.util.*

class NextBirthdayModel: NextBirthdayInteractor {
    override fun getNextBirthdayDate(birthday: String): Calendar {
        val date = SimpleDateFormat("MM.dd", Locale.US).parse(birthday)
        val calendar = GregorianCalendar.getInstance()
        if (date != null) {
            calendar.time = date
        }

        val notifyTime: Calendar = GregorianCalendar.getInstance()

        notifyTime.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        notifyTime.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))

        val offset = countOffset(notifyTime)

        notifyTime.add(Calendar.YEAR, offset)

        return notifyTime
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
