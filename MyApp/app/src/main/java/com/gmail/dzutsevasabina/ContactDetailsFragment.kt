package com.gmail.dzutsevasabina

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.gmail.dzutsevasabina.databinding.FragmentDetailsBinding

class ContactDetailsFragment : Fragment() {

    interface ResultReceiver {
        fun processContact(contact: Contact)
    }

    private var binder: ServiceBinder? = null
    private var _detailsBinding: FragmentDetailsBinding? = null
    private val detailsBinding get() = _detailsBinding!!

    private val resultReceiver: ResultReceiver =
        object : ResultReceiver {
            override fun processContact(contact: Contact) {
                val view: View = detailsBinding.root
                view.post {
                    view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
                    detailsBinding.contactImage.setImageResource(contact.image)
                    detailsBinding.contactName.text = contact.name
                    detailsBinding.contactPhoneNumber1.text = contact.phoneNumber1
                    detailsBinding.contactPhoneNumber2.text = contact.phoneNumber2
                    detailsBinding.contactEmail1.text = contact.email1
                    detailsBinding.contactEmail2.text = contact.email2
                    detailsBinding.contactDescription.text = contact.description
                }
            }

        }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ServiceBinder) {
            binder = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        binder = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val context = context
        if (context is AppCompatActivity) {
            context.supportActionBar?.title = getString(R.string.fragment2_title)
        }

        _detailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        val id = arguments?.getInt("ARG_ID")
        if (id != null) {
            binder?.getService()?.getContactDetail(resultReceiver, id)
        }
        return detailsBinding.root
    }

    companion object {
        fun newInstance(id: Int) = ContactDetailsFragment().apply {
            arguments = bundleOf(
                "ARG_ID" to id
            )
        }
    }
}
