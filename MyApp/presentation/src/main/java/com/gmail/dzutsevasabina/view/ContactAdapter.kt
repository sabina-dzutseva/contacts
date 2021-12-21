package com.gmail.dzutsevasabina.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.dzutsevasabina.databinding.ContactBinding
import com.gmail.dzutsevasabina.model.BriefContact

class ContactAdapter(private val actionListener: (pos: Int) -> Unit):
    ListAdapter<BriefContact, ContactViewHolder>(ContactComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContactBinding.inflate(inflater, parent, false)

        return ContactViewHolder(binding).also {
            it.binding.root.setOnClickListener {
                    view ->
                val position = it.adapterPosition
                if (position != RecyclerView.NO_POSITION){
                actionListener(position)
            }
        } }
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact)
    }

    class ContactComparator : DiffUtil.ItemCallback<BriefContact>() {
        override fun areItemsTheSame(oldItem: BriefContact, newItem: BriefContact): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BriefContact, newItem: BriefContact): Boolean {
            return (oldItem.name == newItem.name) && (oldItem.phoneNumber1 == newItem.phoneNumber1)

        }
    }
}
