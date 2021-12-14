package com.gmail.dzutsevasabina.repository

import android.content.Context
import com.gmail.dzutsevasabina.model.BriefContact
import com.gmail.dzutsevasabina.model.DetailedContact
import io.reactivex.rxjava3.core.Single

interface ContactRepository {
    fun getContact(id: String, context: Context): Single<DetailedContact>
    fun getContactList(context: Context, query: String): Single<List<BriefContact>>
}
