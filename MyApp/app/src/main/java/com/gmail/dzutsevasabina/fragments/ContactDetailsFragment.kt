package com.gmail.dzutsevasabina.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.gmail.dzutsevasabina.BirthdayAlertReceiver
import com.gmail.dzutsevasabina.R
import com.gmail.dzutsevasabina.interfaces.ServiceBinder
import com.gmail.dzutsevasabina.databinding.FragmentDetailsBinding
import com.gmail.dzutsevasabina.interfaces.ButtonClickListener
import com.gmail.dzutsevasabina.model.Contact
import java.text.SimpleDateFormat
import java.util.*

class ContactDetailsFragment : Fragment(), ButtonClickListener {

    interface ResultReceiver {
        fun processContact(contact: Contact)
    }

    private var binder: ServiceBinder? = null
    private var listener: ButtonClickListener? = null

    private var _detailsBinding: FragmentDetailsBinding? = null
    private val detailsBinding get() = _detailsBinding!!

    private val resultReceiver: ResultReceiver =
        object : ResultReceiver {
            override fun processContact(contact: Contact) {
                val view: View = detailsBinding.root
                with(detailsBinding) {
                    view.post {
                        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
                        contactImage.setImageResource(contact.image)

                        contactName.text = contact.name

                        birthdayTitle.text = getString(R.string.birthday)
                        birthday.text = contact.birthday

                        contactPhoneNumber1.text = contact.phoneNumber1
                        contactPhoneNumber2.text = contact.phoneNumber2

                        contactEmail1.text = contact.email1
                        contactEmail2.text = contact.email2

                        contactDescription.text = contact.description

                        val button = birthdayNotificationButton
                        button.setOnClickListener { onButtonClick(button, contact) }
                        button.isChecked = contact.sendBirthdayNotifications
                    }
                }
            }

        }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ServiceBinder) {
            binder = context
        }

        if (context is ButtonClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        binder = null
        listener = null
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

    override fun onButtonClick(button: Button, contact: Contact) {
        val alarmManager: AlarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, BirthdayAlertReceiver::class.java)
        intent.putExtra("CONTACT_ID", arguments?.getInt("ARG_ID"))
        val message: String = String.format(resources.getString(R.string.notification_text, contact.name))
        intent.putExtra("MESSAGE", message)

        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (button is CheckBox) {
            if (button.isChecked) {
                contact.sendBirthdayNotifications = true

                val date = SimpleDateFormat("dd.MM", Locale.US).parse(contact.birthday)
                val calendar = GregorianCalendar.getInstance()
                if (date != null) {
                    calendar.time = date
                }

                val notifyTime: Calendar = GregorianCalendar.getInstance()

                notifyTime.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
                notifyTime.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))

                val offset = countOffset(notifyTime)

                notifyTime.add(Calendar.YEAR, offset)
                alarmManager.set(AlarmManager.RTC, notifyTime.timeInMillis, pendingIntent)
            } else {
                contact.sendBirthdayNotifications = false
                alarmManager.cancel(pendingIntent)
            }
        }
    }

    private fun countOffset(notifyTime: Calendar): Int {

        val year = notifyTime.get(Calendar.YEAR)
        val isSpecialDate =
            (notifyTime.get(Calendar.DAY_OF_MONTH) == 29)
                    && (notifyTime.get(Calendar.MONTH) == Calendar.FEBRUARY)

        var offset = 0

        if (isSpecialDate) {
            if ((year + 1) % 4 == 0) {
                offset = 1
            }

            if ((year + 2) % 4 == 0) {
                offset = 2
            }

            if ((year + 3) % 4 == 0) {
                offset = 3
            }
        }

        if (notifyTime.timeInMillis < System.currentTimeMillis()) {
            offset++
        }

        return offset
    }

    companion object {
        fun newInstance(id: Int) = ContactDetailsFragment().apply {
            arguments = bundleOf(
                "ARG_ID" to id
            )
        }
    }
}

