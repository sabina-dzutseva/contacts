package com.gmail.dzutsevasabina

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gmail.dzutsevasabina.databinding.FragmentListBinding

class ContactListFragment : Fragment() {

    private var listener: View.OnClickListener? = null
    private var _listBinding: FragmentListBinding? = null
    private val listBinding get() = _listBinding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
        val context = context
        if (context is AppCompatActivity) {
            context.supportActionBar?.title = getString(R.string.fragment1_title)
        }

        _listBinding = FragmentListBinding.inflate(inflater, container, false)
        val view = listBinding.root
        view.setOnClickListener { listener?.onClick(view) }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _listBinding = null
    }
}
