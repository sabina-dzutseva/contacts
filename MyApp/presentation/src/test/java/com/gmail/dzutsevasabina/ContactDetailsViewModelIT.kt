package com.gmail.dzutsevasabina

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.gmail.dzutsevasabina.entity.Contact
import com.gmail.dzutsevasabina.interactor.ContactModel
import com.gmail.dzutsevasabina.viewmodel.AlarmHandler
import org.mockito.junit.MockitoJUnitRunner
import com.gmail.dzutsevasabina.viewmodel.AlarmServiceImpl
import com.gmail.dzutsevasabina.viewmodel.ContactDetailsViewModel
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*

@RunWith(MockitoJUnitRunner::class)
class ContactDetailsViewModelIT : ShouldSpec() {

    lateinit var viewModel: ContactDetailsViewModel

    @Mock
    lateinit var mockAlarmService: AlarmServiceImpl

    @Mock
    lateinit var mockContactModel: ContactModel

    lateinit var alarmHandler: AlarmHandler

    lateinit var mockContact: Contact

    var isChecked: Boolean = true

    init {
        listener(InstantExecutorListener())

        beforeTest {
            changeRxScheduler()

            MockitoAnnotations.openMocks(this)

            alarmHandler = AlarmHandler(mockAlarmService)
            mockContactWithData("Иван Иванович", "09.08")
            `when`(mockContactModel.getContact("0")).thenReturn(mockContact)

            viewModel = ContactDetailsViewModel()
            viewModel.alarmHandler = alarmHandler
            viewModel.contactModel = mockContactModel

            viewModel.getContactDetail("0")
        }

        context("Пользователь нажал на контакт") {
            should("Загрузка деталей контакта") {

                val contact = viewModel.getDetails().value

                contact?.id shouldBe "0"
                contact?.name shouldBe "Иван Иванович"
                contact?.birthday shouldBe "09.08"
            }
        }

        context("Пользователь нажал на кнопку включения уведомлений") {
            should("Установка напоминания") {
                val contact = viewModel.getDetails().value
                isChecked = true
                viewModel.handleAlarm("0", isChecked) shouldBe Unit
                contact?.sendBirthdayNotifications shouldBe true
            }
        }

        context("Пользователь выключил уведомления для контакта") {
            should("Отмена напоминания") {
                val contact = viewModel.getDetails().value
                isChecked = false
                viewModel.handleAlarm("0", isChecked)
                contact?.sendBirthdayNotifications shouldBe false
            }
        }
    }


    private fun mockContactWithData(name: String, birthday: String) {
        mockContact = Contact("0", "", name, "", "", "", "", birthday, "")
    }

    private fun changeRxScheduler() {
        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        RxJavaPlugins.reset()
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
    }
}

class InstantExecutorListener : TestListener {
    override suspend fun afterSpec(spec: Spec) {
        super.afterSpec(spec)
        print("Removing Instant Executor Delegate")
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

    override suspend fun beforeSpec(spec: Spec) {
        super.beforeSpec(spec)
        print("Delegating to Instant Executor")
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) = runnable.run()
            override fun postToMainThread(runnable: Runnable) = runnable.run()
            override fun isMainThread(): Boolean = true
        })
    }
}
