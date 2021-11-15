package com.gmail.dzutsevasabina

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.core.content.ContextCompat
import com.gmail.dzutsevasabina.databinding.ActivityMainBinding
import com.gmail.dzutsevasabina.fragments.ContactDetailsFragment
import com.gmail.dzutsevasabina.fragments.ContactListFragment
import com.gmail.dzutsevasabina.interfaces.ServiceBinder
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.gmail.dzutsevasabina.interfaces.ContactClickListener


class MainActivity : AppCompatActivity(), ContactClickListener, ServiceBinder {

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
                    intent.getStringExtra("CONTACT_DETAIL_ID")?.let { addDetailsFragment(it) }
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
        isCreated = savedInstanceState == null

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
                }
            }

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> {
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_CONTACTS
            ) -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.permission_dialog_title))
                    .setMessage(getString(R.string.permission_dialog_message))
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.ok)) { dialog, id ->
                        requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                    }
                    .setNegativeButton(getString(R.string.cancel)) { dialog, id ->
                        dialog.cancel()
                    }
                builder.create().show()
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }

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

    private fun addDetailsFragment(id: String) {
        val fragment2 = ContactDetailsFragment.newInstance(id)
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(binding.fragmentsContainer.id, fragment2)
            .addToBackStack(null)
            .commit()
    }

    override fun onClick(view: View?, id: String) {
        addDetailsFragment(id)
    }
}
