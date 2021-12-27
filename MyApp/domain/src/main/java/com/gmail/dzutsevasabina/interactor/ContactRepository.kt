package com.gmail.dzutsevasabina.interactor

import com.gmail.dzutsevasabina.entity.Contact

interface ContactRepository {
    fun getContact(id: String): Contact?
    fun getContactList(query: String): List<Contact>
}
