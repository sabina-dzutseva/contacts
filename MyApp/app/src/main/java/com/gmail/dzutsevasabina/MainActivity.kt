package com.gmail.dzutsevasabina

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.gmail.dzutsevasabina.databinding.ActivityMainBinding
import com.gmail.dzutsevasabina.fragments.ContactDetailsFragment
import com.gmail.dzutsevasabina.fragments.ContactListFragment
import com.gmail.dzutsevasabina.interfaces.ServiceBinder

class MainActivity : AppCompatActivity(), View.OnClickListener, ServiceBinder {

    private lateinit var binding: ActivityMainBinding
    private lateinit var broadcastReceiver: BirthdayAlertReceiver

    private var service: ContactService? = null
    private var isBound: Boolean = false
    private var isCreated: Boolean = false
    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            val b = binder as ContactService.ContactBinder
            service = b.getService()
            isBound = true

            if (isCreated) {
                if (intent.getIntExtra("FRAGMENT_ID", 0) == R.layout.fragment_details) {
                    addDetailsFragment(intent.getIntExtra("CONTACT_DETAIL_ID", 0))
                } else {
                    if (isCreated) {
                        addListFragment()
                    }
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }

    override fun getService(): ContactService? = service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        broadcastReceiver = BirthdayAlertReceiver()
        registerReceiver(broadcastReceiver, IntentFilter(ALARM_SERVICE))

        val intent = Intent(this, ContactService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

        isCreated = savedInstanceState == null
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
        unregisterReceiver(broadcastReceiver)
    }

    private fun addListFragment() {
        val fragment1 = ContactListFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .add(binding.fragmentsContainer.id, fragment1)
            .commit()
    }

    private fun addDetailsFragment(id: Int) {
        val fragment2 = ContactDetailsFragment.newInstance(id)
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(binding.fragmentsContainer.id, fragment2)
            .addToBackStack(null)
            .commit()
    }

    override fun onClick(view: View?) {
        addDetailsFragment(0)
    }
}
