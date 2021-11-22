package com.gmail.dzutsevasabina.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gmail.dzutsevasabina.R
import com.gmail.dzutsevasabina.databinding.FragmentListBinding
import com.gmail.dzutsevasabina.view.interfaces.ContactClickListener
import com.gmail.dzutsevasabina.model.BriefContact
import com.gmail.dzutsevasabina.viewmodel.ContactListViewModel

class ContactListFragment : Fragment() {
    private var listener: ContactClickListener? = null

    private var _listBinding: FragmentListBinding? = null
    private val listBinding get() = _listBinding!!

    private lateinit var listViewModel: ContactListViewModel
    private lateinit var observer: Observer<ArrayList<BriefContact>>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContactClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val context = context
        _listBinding = FragmentListBinding.inflate(inflater, container, false)

        if (context is AppCompatActivity) {
            context.supportActionBar?.title = getString(R.string.fragment1_title)

            observer = Observer { setViews(it) }
            listViewModel = ViewModelProvider(context).get(ContactListViewModel::class.java)
            listViewModel.liveData.observe(context, observer)
        }

        if (context != null) {
            listViewModel.getContactsList(context)
        }

        return listBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _listBinding = null
        listViewModel.liveData.removeObserver(observer)
    }

    private fun setViews(contacts: ArrayList<BriefContact>) {
        val view: View = listBinding.root
        with(listBinding) {
            view.post {
                background.setBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.white
                    )
                )
                contactImage.setImageURI(Uri.parse(contacts[0].image))
                contactName.text = contacts[0].name
                contactPhoneNumber.text = contacts[0].phoneNumber1
                view.setOnClickListener { listener?.onClick(view, contacts[0].id) }
            }
        }
    }
}
