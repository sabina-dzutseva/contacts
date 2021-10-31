package com.gmail.dzutsevasabina.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gmail.dzutsevasabina.R
import com.gmail.dzutsevasabina.interfaces.ServiceBinder
import com.gmail.dzutsevasabina.databinding.FragmentListBinding
import com.gmail.dzutsevasabina.model.Contact

class ContactListFragment : Fragment() {

    interface ResultReceiver {
        fun processList(contacts: ArrayList<Contact>)
    }

    private var listener: View.OnClickListener? = null
    private var binder: ServiceBinder? = null
    private var _listBinding: FragmentListBinding? = null
    private val listBinding get() = _listBinding!!

    private val resultReceiver: ResultReceiver = object : ResultReceiver {
        override fun processList(contacts: ArrayList<Contact>) {
            val view: View = listBinding.root
            with(listBinding) {
                view.post {
                    background.setBackgroundColor(
                        ContextCompat.getColor(
                            view.context,
                            R.color.white
                        )
                    )
                    contactImage.setImageResource(contacts[0].image)
                    contactName.text = contacts[0].name
                    contactPhoneNumber.text = contacts[0].phoneNumber1
                    view.setOnClickListener { listener?.onClick(view) }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is View.OnClickListener) {
            listener = context
        }

        if (context is ServiceBinder) {
            binder = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        binder = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val context = context
        if (context is AppCompatActivity) {
            context.supportActionBar?.title = getString(R.string.fragment1_title)
        }

        _listBinding = FragmentListBinding.inflate(inflater, container, false)
        binder?.getService()?.getContactsList(resultReceiver)
        return listBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _listBinding = null
    }
}
