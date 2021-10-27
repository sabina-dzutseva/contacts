package com.gmail.dzutsevasabina

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.concurrent.*

class ContactService : Service() {

    private val binder = ContactBinder()
    private val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    fun getContactsList(receiver: ContactListFragment.ResultReceiver) {
        executor.schedule({ receiver.processList(contacts) }, 5, TimeUnit.SECONDS)
    }

    fun getContactDetail(receiver: ContactDetailsFragment.ResultReceiver, id: Int) {
        executor.schedule({ receiver.processContact(contacts[id]) }, 2, TimeUnit.SECONDS)
    }

    inner class ContactBinder : Binder() {
        fun getService(): ContactService = this@ContactService
    }
}
