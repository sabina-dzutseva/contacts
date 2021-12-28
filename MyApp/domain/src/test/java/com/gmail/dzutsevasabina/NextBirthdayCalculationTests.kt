package com.gmail.dzutsevasabina

import com.gmail.dzutsevasabina.interactor.NextBirthdayModel
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.util.*

class NextBirthdayCalculationTests : ShouldSpec() {

    init {
        val nextBirthdayModel = NextBirthdayModel()
        val calendar = GregorianCalendar.getInstance()
        val expected = GregorianCalendar.getInstance()

        context("Текущий год - не високосный, день рождения в этом году уже был") {
            should("Следующий день рождения в следующем году") {
                calendar.set(1999, 9, 9)
                expected.set(2000, 9, 8)
                val actual = nextBirthdayModel.getNextBirthdayDate("09.08", calendar)
                dateCheck(expected, actual)
            }
        }

        context("Текущий год - не високосный, дня рождения в этом году ещё не было") {
            should("Следующий день рождения в текущем году") {
                calendar.set(1999, 9, 7)
                expected.set(1999, 9, 8)
                val actual = nextBirthdayModel.getNextBirthdayDate("09.08", calendar)
                dateCheck(expected, actual)
            }
        }

        context("Текущий год - не високосный, день рождения контакта 29 февраля") {
            should("Следующий день рождения в сдледующем високосном году") {
                calendar.set(1999, 3, 2)
                expected.set(2000, 2, 29)
                val actual = nextBirthdayModel.getNextBirthdayDate("02.29", calendar)
                dateCheck(expected, actual)
            }
        }

        context("Текущий год - високосный, день рождения контакта 29 февраля, день рождения уже был") {
            should("Следующий день рождения через 4 года") {
                calendar.set(2000, 3, 1)
                expected.set(2004, 2, 29)
                val actual = nextBirthdayModel.getNextBirthdayDate("02.29", calendar)
                dateCheck(expected, actual)
            }
        }
    }

    private fun dateCheck(expected: Calendar, actual: Calendar) {
        actual.get(Calendar.DAY_OF_MONTH) shouldBe expected.get(Calendar.DAY_OF_MONTH)
        actual.get(Calendar.MONTH) shouldBe expected.get(Calendar.MONTH)
        actual.get(Calendar.YEAR) shouldBe expected.get(Calendar.YEAR)
    }

}
