package com.gmail.dzutsevasabina.viewmodel.mapper

import com.gmail.dzutsevasabina.entity.Contact
import com.gmail.dzutsevasabina.model.BriefContact

class BriefContactMapper : Mapper<Contact, BriefContact> {
    override fun transform(data: Contact): BriefContact =
        BriefContact(data.id, data.image, data.name, data.phoneNumber1)
}
