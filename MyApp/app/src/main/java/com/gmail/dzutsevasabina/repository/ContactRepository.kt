package com.gmail.dzutsevasabina.repository

import android.content.Context
import com.gmail.dzutsevasabina.model.BriefContact
import com.gmail.dzutsevasabina.model.DetailedContact

interface ContactRepository {
    fun getContact(id: String, context: Context): DetailedContact?
    fun getContactList(context: Context): ArrayList<BriefContact>
}
