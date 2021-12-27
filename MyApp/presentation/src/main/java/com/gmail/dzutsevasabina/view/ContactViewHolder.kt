package com.gmail.dzutsevasabina.view

import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.gmail.dzutsevasabina.databinding.ContactBinding
import com.gmail.dzutsevasabina.model.BriefContact

class ContactViewHolder(val binding: ContactBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(contact: BriefContact) {
        itemView.tag = contact
        with(binding) {
            root.post {
                background.setBackgroundColor(getColor(root.context, com.gmail.dzutsevasabina.R.color.white))
                contactImage.setImageURI(android.net.Uri.parse(contact.image))
                contactName.text = contact.name
                contactPhoneNumber.text = contact.phoneNumber1
            }
        }
    }
}
