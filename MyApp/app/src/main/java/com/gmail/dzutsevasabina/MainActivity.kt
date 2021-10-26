package com.gmail.dzutsevasabina

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gmail.dzutsevasabina.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            addListFragment()
        }
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
