package com.gmail.dzutsevasabina.viewmodel.mapper

import com.gmail.dzutsevasabina.entity.Contact
import com.gmail.dzutsevasabina.model.DetailedContact

class DetailedContactMapper : Mapper<Contact, DetailedContact> {
    override fun transform(data: Contact): DetailedContact =
        DetailedContact(
            data.id,
            data.image,
            data.name,
            data.phoneNumber1,
            data.phoneNumber2,
            data.email1,
            data.email2,
            data.birthday,
            data.description
        )
}
