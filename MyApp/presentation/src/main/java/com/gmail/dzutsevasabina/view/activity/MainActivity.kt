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
import com.gmail.dzutsevasabina.view.*
import com.gmail.dzutsevasabina.view.fragment.ContactDetailsFragment
import com.gmail.dzutsevasabina.view.fragment.ContactListFragment
import com.gmail.dzutsevasabina.view.fragment.MapFragment
import com.gmail.dzutsevasabina.view.fragment.RouteFragment

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

        supportFragmentManager.setFragmentResultListener(
            DETAILS_CONTACT_ID_REQUEST_KEY,
            this
        ) { requestKey, bundle ->
            val id = bundle.getInt(ID_KEY)
            addDetailsFragment(id)
        }

        supportFragmentManager.setFragmentResultListener(
            ROUTE_ID_REQUEST_KEY,
            this
        ) { requestKey, bundle ->
            addRouteFragment()
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .add(binding.fragmentsContainer.id, fragmentList)
            .commit()
    }

    private fun addDetailsFragment(id: Int) {
        supportFragmentManager.setFragmentResultListener(
            MAP_CONTACT_ID_REQUEST_KEY,
            this
        ) { requestKey, bundle ->
            val keyId = bundle.getInt(ID_KEY)
            addMapFragment(keyId)
        }

        val fragmentDetails = ContactDetailsFragment.newInstance(id)
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(binding.fragmentsContainer.id, fragmentDetails)
            .addToBackStack(null)
            .commit()
    }

    private fun addMapFragment(id: Int) {
        val fragmentMap = MapFragment.newInstance(id)
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(binding.fragmentsContainer.id, fragmentMap)
            .addToBackStack(null)
            .commit()
    }

    private fun addRouteFragment() {
        val fragmentRoute = RouteFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(binding.fragmentsContainer.id, fragmentRoute)
            .addToBackStack(null)
            .commit()
    }

    private fun addFragment() {
        if (isCreated) {
            if (intent.getIntExtra(FRAGMENT_ID, 0) == R.layout.fragment_details) {
                val extra = intent.getIntExtra(CONTACT_DETAIL_ID, -1)
                addDetailsFragment(extra)
            } else if (intent.getIntExtra(FRAGMENT_ID, 0) == R.layout.fragment_map) {
                val extra = intent.getIntExtra(CONTACT_DETAIL_ID, -1)
                addMapFragment(extra)
            } /*else if (intent.getIntExtra(FRAGMENT_ID, 0) == R.layout.fragment_route) {
                addRouteFragment()
            }*/ else {
                addListFragment()
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
