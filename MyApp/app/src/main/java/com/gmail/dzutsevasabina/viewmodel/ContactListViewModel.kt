package com.gmail.dzutsevasabina.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.dzutsevasabina.model.BriefContact
import com.gmail.dzutsevasabina.repository.ContactRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ContactListViewModel : ViewModel() {
    private val list = MutableLiveData<List<BriefContact>>()
    private val loadStatus = MutableLiveData<Boolean>()
    private val contactRepository = ContactRepositoryImpl()
    private val disposable: CompositeDisposable = CompositeDisposable()

    fun getContactsList(context: Context, query: String) {
       disposable
            .add(contactRepository.getContactList(context, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                loadStatus.postValue(true)
            }
            .doOnTerminate {
                loadStatus.postValue(false)
            }
            .subscribe (
                {list.postValue(it)},
                {loadStatus.postValue(false)}
            ))
    }

    fun getContactId(pos: Int): String? = list.value?.get(pos)?.id

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
