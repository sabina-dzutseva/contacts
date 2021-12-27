package com.gmail.dzutsevasabina.view.activity

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.gmail.dzutsevasabina.databinding.ActivityMainBinding
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.gmail.dzutsevasabina.viewmodel.BirthdayAlertReceiver
import com.gmail.dzutsevasabina.R
import com.gmail.dzutsevasabina.view.CONTACT_DETAIL_ID
import com.gmail.dzutsevasabina.view.CONTACT_ID_REQUEST_KEY
import com.gmail.dzutsevasabina.view.FRAGMENT_ID
import com.gmail.dzutsevasabina.view.ID_KEY
import com.gmail.dzutsevasabina.view.fragment.ContactDetailsFragment
import com.gmail.dzutsevasabina.view.fragment.ContactListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var broadcastReceiver: BirthdayAlertReceiver
    private var isCreated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        broadcastReceiver = BirthdayAlertReceiver()
        registerReceiver(broadcastReceiver, IntentFilter(ALARM_SERVICE))
        isCreated = savedInstanceState == null

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    addFragment()
                }
            }

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> {
                addFragment()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_CONTACTS
            ) -> {
                createDialog(requestPermissionLauncher)
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    private fun addListFragment() {
        val fragmentList = ContactListFragment()

        supportFragmentManager.setFragmentResultListener(CONTACT_ID_REQUEST_KEY, this) { requestKey, bundle ->
            val id = bundle.getString(ID_KEY)

            if (id != null) {
                addDetailsFragment(id.toString())
            }
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .add(binding.fragmentsContainer.id, fragmentList)
            .commit()
    }

    private fun addDetailsFragment(id: String) {
        val fragmentDetails = ContactDetailsFragment.newInstance(id)
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(binding.fragmentsContainer.id, fragmentDetails)
            .addToBackStack(null)
            .commit()
    }

    private fun addFragment() {
        if (isCreated) {
            if (intent.getIntExtra(FRAGMENT_ID, 0) == R.layout.fragment_details) {
                val extra = intent.getStringExtra(CONTACT_DETAIL_ID)
                if (extra != null) {
                    addDetailsFragment(extra)
                }
            } else {
                if (isCreated) {
                    addListFragment()
                }
            }
        }
    }

    private fun createDialog(requestPermissionLauncher: ActivityResultLauncher<String>) {
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
}
