package com.gmail.dzutsevasabina

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.gmail.dzutsevasabina.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener, ServiceBinder {

    private lateinit var binding: ActivityMainBinding

    private var service: ContactService? = null
    private var isBound: Boolean = false
    private var isCreated: Boolean = false
    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            val b = binder as ContactService.ContactBinder
            service = b.getService()
            isBound = true

            if (isCreated) {
                addListFragment()
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

        val intent = Intent(this, ContactService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

        isCreated = savedInstanceState == null
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }

    private fun addListFragment() {
        val fragment1 = ContactListFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .add(binding.fragmentsContainer.id, fragment1)
            .commit()
    }

    private fun addDetailsFragment() {
        val fragment2 = ContactDetailsFragment.newInstance(0)
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(binding.fragmentsContainer.id, fragment2)
            .addToBackStack(null)
            .commit()
    }

    override fun onClick(p0: View?) {
        addDetailsFragment()
    }
}
