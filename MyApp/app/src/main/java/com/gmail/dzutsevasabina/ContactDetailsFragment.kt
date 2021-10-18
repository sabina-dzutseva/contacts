package com.gmail.dzutsevasabina

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.gmail.dzutsevasabina.databinding.FragmentDetailsBinding

class ContactDetailsFragment : Fragment() {

    private var _detailsBinding: FragmentDetailsBinding? = null
    private val detailsBinding get() = _detailsBinding!!

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
        return detailsBinding.root
    }

    companion object {
        fun newInstance(id: Int) = ContactDetailsFragment().apply {
            arguments = bundleOf(
                "ARG_ID" to id
            )
        }
    }
}
