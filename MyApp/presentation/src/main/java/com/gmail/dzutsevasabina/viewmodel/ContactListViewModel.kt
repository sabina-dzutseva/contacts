package com.gmail.dzutsevasabina.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.dzutsevasabina.interactor.ContactInteractor
import com.gmail.dzutsevasabina.model.BriefContact
import com.gmail.dzutsevasabina.viewmodel.mapper.BriefContactMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ContactListViewModel @Inject constructor(var contactModel: ContactInteractor) : ViewModel() {
    private val list = MutableLiveData<List<BriefContact>>()
    private val loadStatus = MutableLiveData<Boolean>()
    private val disposable: CompositeDisposable = CompositeDisposable()
    private val mapper: BriefContactMapper = BriefContactMapper()

    fun getContactsList(query: String) {
        disposable
            .add(Single.fromCallable {
                val contactList = contactModel.getContactList(query)
                list.postValue(contactList.map { mapper.transform(it) })
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

    fun getContactId(pos: Int) = list.value?.get(pos)?.id

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun getList(): LiveData<List<BriefContact>> {
        return list
    }

    fun getLoadStatus(): LiveData<Boolean> {
        return loadStatus
    }
}
