package com.gmail.dzutsevasabina.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.dzutsevasabina.model.BriefContact
import com.gmail.dzutsevasabina.repository.ContactRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ContactListViewModel : ViewModel() {
    val list = MutableLiveData<ArrayList<BriefContact>>()
    val loadStatus = MutableLiveData<Boolean>()
    private val contactRepository = ContactRepositoryImpl()
    private val disposable: CompositeDisposable = CompositeDisposable()

    fun getContactsList(context: Context, query: String) {
        disposable
            .add(Single.fromCallable {
                list.postValue(contactRepository.getContactList(context, query))
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

    fun getContactId(pos: Int): String? = list.value?.get(pos)?.id

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
