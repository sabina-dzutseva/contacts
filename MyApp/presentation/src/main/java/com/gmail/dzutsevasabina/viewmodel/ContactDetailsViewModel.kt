package com.gmail.dzutsevasabina.viewmodel

import android.content.Context
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.dzutsevasabina.di.IApp
import com.gmail.dzutsevasabina.interactor.ContactModel
import com.gmail.dzutsevasabina.model.DetailedContact
import com.gmail.dzutsevasabina.viewmodel.mapper.DetailedContactMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ContactDetailsViewModel : ViewModel() {
    private val details = MutableLiveData<DetailedContact>()
    private val loadStatus = MutableLiveData<Boolean>()
    @Inject
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

    fun handleAlarm(id: String?, button: Button) {
        val contact = details.value ?: return
        alarmHandler.handleAlarm(contact, id, button)
    }
}
