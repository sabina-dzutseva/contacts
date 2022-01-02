package com.gmail.dzutsevasabina.interactor

import java.text.SimpleDateFormat
import java.util.*

class NextBirthdayModel: NextBirthdayInteractor {
    override fun getNextBirthdayDate(birthday: String, currentDate: Calendar): Calendar {
        val date = SimpleDateFormat("yyyy.MM.dd", Locale.US).parse("1972.$birthday")

        val calendar = GregorianCalendar.getInstance()
        if (date != null) {
            calendar.time = date
            calendar.add(Calendar.MONTH, 1)
        }

        val notifyTime: Calendar = GregorianCalendar.getInstance()
        notifyTime.set(Calendar.YEAR, currentDate.get(Calendar.YEAR))

        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val isSpecialDate =
            (day == 29 && month == 2)

        var offset = countOffset(notifyTime, isSpecialDate)
        notifyTime.add(Calendar.YEAR, offset)

        notifyTime.set(Calendar.MONTH, month)
        notifyTime.set(Calendar.DAY_OF_MONTH, day)

        if (notifyTime.timeInMillis < currentDate.timeInMillis) {
            offset += if (isSpecialDate) 4 else 1
            notifyTime.add(Calendar.YEAR, offset)
        }

        notifyTime.set(Calendar.HOUR_OF_DAY, 9)
        notifyTime.set(Calendar.MINUTE, 0)
        notifyTime.set(Calendar.SECOND, 0)

        return notifyTime
    }

    private fun countOffset(notifyTime: Calendar, isSpecialDate: Boolean): Int {
        val year = notifyTime.get(Calendar.YEAR)

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

        return offset
    }
}
