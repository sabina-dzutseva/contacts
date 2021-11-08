package com.gmail.dzutsevasabina

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.content.ContextCompat
import com.gmail.dzutsevasabina.fragments.ContactDetailsFragment
import com.gmail.dzutsevasabina.fragments.ContactListFragment
import com.gmail.dzutsevasabina.model.contacts
import java.util.concurrent.*
import java.util.jar.Manifest

class ContactService : Service() {

    private val binder = ContactBinder()
    private val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    fun getContactsList(receiver: ContactListFragment.ResultReceiver) {
        executor.schedule({ receiver.processList(contacts) }, 5, TimeUnit.MILLISECONDS)
    }

    fun getContactDetail(receiver: ContactDetailsFragment.ResultReceiver, id: Int) {
        executor.schedule({ receiver.processContact(contacts[id]) }, 2, TimeUnit.MILLISECONDS)
    }

    inner class ContactBinder : Binder() {
        fun getService(): ContactService = this@ContactService
    }
}
