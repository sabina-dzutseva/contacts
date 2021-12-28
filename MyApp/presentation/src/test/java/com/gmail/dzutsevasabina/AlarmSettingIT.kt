package com.gmail.dzutsevasabina

import android.app.AlarmManager
import android.content.Context
import android.widget.CheckBox
import com.gmail.dzutsevasabina.model.DetailedContact
import com.gmail.dzutsevasabina.viewmodel.AlarmHandler
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import android.test.mock.MockContext
import com.gmail.dzutsevasabina.interactor.AlarmService
import com.gmail.dzutsevasabina.interactor.NextBirthdayModel
import com.gmail.dzutsevasabina.viewmodel.AlarmServiceImpl
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoRule
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class AlarmSettingIT : ShouldSpec() {

    @Mock
    lateinit var mockAlarmService: AlarmServiceImpl

    lateinit var alarmHandler: AlarmHandler

    @Mock
    lateinit var mockButton: CheckBox

    @Mock
    lateinit var mockContact: DetailedContact

    lateinit var expectedNextAlarm: Calendar

    init {
        beforeTest {
            MockitoAnnotations.openMocks(this)
            alarmHandler = AlarmHandler(mockAlarmService)
            mockContactWithData("Иван Иванович", "09.08", false)
        }

        context("Проверка добавления и удаления напоминания одного и того же контакта") {
            should("Выполняется добавление напоминания") {
                `when`(mockButton.isChecked).thenReturn(true)

                alarmHandler.handleAlarm(
                    mockContact,
                    "1",
                    mockButton,
                    GregorianCalendar.getInstance()
                ) shouldBe Unit
            }

            should("Выполняется удаление напоминания") {
                `when`(mockButton.isChecked).thenReturn(false)
                mockContactWithData("Иван Иванович", "09.08", false)

                alarmHandler.handleAlarm(
                    mockContact,
                    "1",
                    mockButton,
                    GregorianCalendar.getInstance()
                ) shouldBe Unit
            }
        }
    }

    private fun mockContactWithData(name: String, birthday: String, notifications: Boolean) {
        `when`(mockContact.name).thenReturn(name)
        `when`(mockContact.birthday).thenReturn(birthday)
        `when`(mockContact.sendBirthdayNotifications).thenReturn(notifications)
    }
}
