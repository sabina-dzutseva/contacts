package com.gmail.dzutsevasabina.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.dzutsevasabina.model.BriefContact
import com.gmail.dzutsevasabina.repository.ContactRepositoryImpl
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class ContactListViewModel : ViewModel() {
    val liveData = MutableLiveData<ArrayList<BriefContact>>()
    private val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    private val contactRepository = ContactRepositoryImpl()

    fun getContactsList(context: Context, query: String) {
        executor.execute {
            liveData.postValue(contactRepository.getContactList(context, query))
        }
    }

    fun getContactId(pos: Int): String? = liveData.value?.get(pos)?.id
}
