package com.gmail.dzutsevasabina.interactor

import com.gmail.dzutsevasabina.entity.Contact

class ContactModel(private val contactRepository: ContactRepository) : ContactInteractor {

    override fun getContact(id: String): Contact? {
        return contactRepository.getContact(id)
    }

    override fun getContactList(query: String): List<Contact> {
        return contactRepository.getContactList(query)
    }
}
