package com.gmail.dzutsevasabina.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gmail.dzutsevasabina.R
import com.gmail.dzutsevasabina.databinding.FragmentDetailsBinding
import com.gmail.dzutsevasabina.di.IApp
import com.gmail.dzutsevasabina.model.DetailedContact
import com.gmail.dzutsevasabina.viewmodel.ContactDetailsViewModel
import javax.inject.Inject

const val ARG_ID = "ARG_ID"

class ContactDetailsFragment : Fragment(), View.OnClickListener {

    private var listener: View.OnClickListener? = null

    private var _detailsBinding: FragmentDetailsBinding? = null
    private val detailsBinding get() = _detailsBinding!!

    var detailsViewModel: ContactDetailsViewModel? = null
    @Inject set

    private var progressBar: ProgressBar? = null

    private var checkBoxTitle: TextView? = null
    private var checkBox: CheckBox? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val appComp = (activity?.application as IApp).getAppComponent(context)

        val detailsComponent = appComp?.plusContactDetailsComponent()
        detailsComponent?.inject(this)

        val viewModelComponent = appComp?.plusViewModelComponent()
        viewModelComponent?.inject(detailsViewModel)

        if (context is View.OnClickListener) {
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

        _detailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)

        val context = context
        if (context is AppCompatActivity) {
            context.supportActionBar?.title = getString(R.string.fragment2_title)

            detailsViewModel?.getDetails()?.observe(context) {
                setViews(it)
            }

            progressBar = detailsBinding.progressBarDetails
            checkBoxTitle = detailsBinding.birthdayNotificationTitle
            checkBox = detailsBinding.birthdayNotificationButton

            detailsViewModel?.getLoadStatus()?.observe(viewLifecycleOwner) {
                progressBar?.visibility = if (it) View.VISIBLE else View.GONE

                val checkBoxVisibility = if (it) View.GONE else View.VISIBLE
                checkBoxTitle?.visibility = checkBoxVisibility
                checkBox?.visibility = checkBoxVisibility
            }
        }

        val id = arguments?.getString(ARG_ID)

        if (id != null && context != null) {
            detailsViewModel?.getContactDetail(id)
        }


        return detailsBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        progressBar = null
        checkBoxTitle = null
        checkBox = null
    }

    private fun setViews(contact: DetailedContact) {
        val view: View = detailsBinding.root
        with(detailsBinding) {
            view.post {
                view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
                contactImage.setImageURI(Uri.parse(contact.image))

                contactName.text = contact.name

                birthdayTitle.text = getString(R.string.birthday)
                birthday.text = contact.birthday

                contactPhoneNumber1.text = contact.phoneNumber1
                contactPhoneNumber2.text = contact.phoneNumber2

                contactEmail1.text = contact.email1
                contactEmail2.text = contact.email2

                contactDescription.text = contact.description

                val button = birthdayNotificationButton
                button.isChecked = contact.sendBirthdayNotifications
                button.setOnClickListener { onClick(button) }
            }
        }
    }

    companion object {
        fun newInstance(id: String) = ContactDetailsFragment().apply {
            arguments = bundleOf(
                ARG_ID to id
            )
        }
    }

    override fun onClick(view: View?) {
        if (view is CheckBox) {
            detailsViewModel?.handleAlarm(
                arguments?.getString(ARG_ID),
                view.isChecked)
        }
    }
}
