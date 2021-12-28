package com.gmail.dzutsevasabina.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.dzutsevasabina.interactor.ContactModel
import com.gmail.dzutsevasabina.model.DetailedContact
import com.gmail.dzutsevasabina.viewmodel.mapper.DetailedContactMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class ContactDetailsViewModel : ViewModel() {
    private val details = MutableLiveData<DetailedContact>()
    private val loadStatus = MutableLiveData<Boolean>()

    lateinit var contactModel: ContactModel
    @Inject set

    lateinit var alarmHandler: AlarmHandler
    @Inject set

    private val disposable: CompositeDisposable = CompositeDisposable()
    private val mapper: DetailedContactMapper = DetailedContactMapper()

    fun getDetails(): LiveData<DetailedContact> {
        return details
    }

    fun getLoadStatus(): LiveData<Boolean> {
        return loadStatus
    }

    fun getContactDetail(id: String) {
        disposable
            .add(Single.fromCallable {
                val contact = contactModel.getContact(id)
                if (contact != null) {
                    details.postValue(mapper.transform(contact))
                }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadStatus.postValue(true)
                }
                .doOnTerminate {
                    loadStatus.postValue(false)
                }
                .subscribe())
    }

    fun handleAlarm(id: String?, isChecked: Boolean) {
        val contact = details.value ?: return
        alarmHandler.handleAlarm(contact, id, isChecked, GregorianCalendar.getInstance())
    }
}
