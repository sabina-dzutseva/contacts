package com.gmail.dzutsevasabina.interactor

import com.gmail.dzutsevasabina.entity.Contact

interface ContactInteractor {
    fun getContact(id: Int): Contact?
    fun getContactList(query: String): List<Contact>
}
