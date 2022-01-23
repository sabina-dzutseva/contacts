package com.gmail.dzutsevasabina.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmail.dzutsevasabina.R
import com.gmail.dzutsevasabina.databinding.FragmentDetailsBinding
import com.gmail.dzutsevasabina.di.IApp
import com.gmail.dzutsevasabina.model.DetailedContact
import com.gmail.dzutsevasabina.view.ARG_ID
import com.gmail.dzutsevasabina.view.ID_KEY
import com.gmail.dzutsevasabina.view.MAP_CONTACT_ID_REQUEST_KEY
import com.gmail.dzutsevasabina.view.setTextOrChangeVisibility
import com.gmail.dzutsevasabina.viewmodel.ContactDetailsViewModel
import com.gmail.dzutsevasabina.viewmodel.ViewModelFactory
import javax.inject.Inject

class ContactDetailsFragment : Fragment() {

    private var listener: View.OnClickListener? = null

    private var _detailsBinding: FragmentDetailsBinding? = null
    private val detailsBinding get() = _detailsBinding!!

    lateinit var viewModelFactory: ViewModelFactory
    @Inject set

    var detailsViewModel: ContactDetailsViewModel? = null

    private var progressBar: ProgressBar? = null

    private var checkBoxTitle: TextView? = null
    private var checkBox: CheckBox? = null

    private var mapLocationTitle: TextView? = null
    private var mapLocationButton: ImageButton? = null

    private var notificationViewsVisibility = true


    override fun onAttach(context: Context) {
        super.onAttach(context)

        val appComp = (activity?.application as IApp).getAppComponent(context)

        val detailsComponent = appComp?.plusContactDetailsComponent()
        detailsComponent?.inject(this)

        detailsViewModel = ViewModelProvider(this, viewModelFactory)[ContactDetailsViewModel::class.java]

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
            context.supportActionBar?.title = getString(R.string.details_fragment_title)

            detailsViewModel?.getDetails()?.observe(viewLifecycleOwner) {
                setViews(it)
            }

            with(detailsBinding) {
                progressBar = progressBarDetails
                checkBoxTitle = birthdayNotificationTitle
                checkBox = birthdayNotificationButton
                mapLocationTitle = locationTitle
                mapLocationButton = locationButton
            }


            detailsViewModel?.getLoadStatus()?.observe(viewLifecycleOwner) {
                progressBar?.isVisible = it

                var visibility = if (it) false else notificationViewsVisibility
                checkBoxTitle?.isVisible = visibility
                checkBox?.isVisible = visibility

                visibility = !it
                mapLocationTitle?.isVisible = visibility
                mapLocationButton?.isVisible = visibility
            }
        }

        val id = arguments?.getInt(ARG_ID)

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
        mapLocationTitle = null
        mapLocationButton = null
    }

    private fun setViews(contact: DetailedContact) {
        val view: View = detailsBinding.root
        with(detailsBinding) {
            view.post {
                view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
                contactImage.setImageURI(Uri.parse(contact.image))

                contactName.text = contact.name

                birthday.text = contact.birthday

                contactPhoneNumber1.setTextOrChangeVisibility(contact.phoneNumber1)
                contactPhoneNumber2.setTextOrChangeVisibility(contact.phoneNumber2)

                contactEmail1.setTextOrChangeVisibility(contact.email1)
                contactEmail2.setTextOrChangeVisibility(contact.email2)

                contactDescription.setTextOrChangeVisibility(contact.description)

                if (contact.birthday.isNotEmpty()) {
                    val button = birthdayNotificationButton
                    button.isChecked = contact.sendBirthdayNotifications
                    button.setOnClickListener { if (it is CheckBox) onCheckBoxClick(it) }
                    notificationViewsVisibility = true
                } else {
                    notificationViewsVisibility = false
                }

                birthdayTitle.isVisible = notificationViewsVisibility
                birthday.isVisible = notificationViewsVisibility
                birthdayNotificationTitle.isVisible = notificationViewsVisibility
                birthdayNotificationButton.isVisible = notificationViewsVisibility

                phoneNumbersTitle.isVisible = contactPhoneNumber1.isVisible
                emailsTitle.isVisible = contactEmail1.isVisible
                descriptionTitle.isVisible = contactDescription.isVisible

                val mapButton = locationButton
                mapButton.setOnClickListener { onMapButtonClick() }
            }
        }
    }

    companion object {
        fun newInstance(id: Int) = ContactDetailsFragment().apply {
            arguments = bundleOf(
                ARG_ID to id
            )
        }
    }

    private fun onMapButtonClick() {
        val context = context
        val id = arguments?.getInt(ARG_ID)
        if (id != null) {
            if (context is AppCompatActivity) {
                context.supportFragmentManager.setFragmentResult(
                    MAP_CONTACT_ID_REQUEST_KEY,
                    bundleOf(ID_KEY to id)
                )
            }
        }
    }

    private fun onCheckBoxClick(button: CheckBox) {
        val id = arguments?.getInt(ARG_ID)
        if (id != null && id != -1) {
            detailsViewModel?.handleAlarm(
                id,
                button.isChecked)
        }
    }
}
