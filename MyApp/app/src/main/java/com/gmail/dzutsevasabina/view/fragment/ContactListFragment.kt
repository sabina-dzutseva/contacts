package com.gmail.dzutsevasabina.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.dzutsevasabina.R
import com.gmail.dzutsevasabina.databinding.FragmentListBinding
import com.gmail.dzutsevasabina.view.*
import com.gmail.dzutsevasabina.viewmodel.ContactListViewModel

const val OFFSET = 20

class ContactListFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _listBinding: FragmentListBinding? = null
    private val listBinding get() = _listBinding!!

    private lateinit var listViewModel: ContactListViewModel

    private var progressBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        val context = context
        _listBinding = FragmentListBinding.inflate(inflater, container, false)

        if (context is AppCompatActivity) {
            context.supportActionBar?.title = getString(R.string.fragment1_title)

            listViewModel = ViewModelProvider(context).get(ContactListViewModel::class.java)

            val adapter by lazy {
                ContactAdapter { pos: Int ->
                    val id = listViewModel.getContactId(pos)
                    if (id != null) {
                        context.supportFragmentManager.setFragmentResult(
                            CONTACT_ID_REQUEST_KEY,
                            bundleOf(ID_KEY to id)
                        )
                    }
                }
            }

            with(listBinding.recycler) {
                layoutManager = LinearLayoutManager(context)
                this.adapter = adapter
                addItemDecoration(OffsetDecorator(OFFSET.dpToPx))
            }

            listViewModel.getList().observe(viewLifecycleOwner) {
                if (it != null) {
                    adapter.submitList(it)
                }
            }

            progressBar = listBinding.progressBarList

            listViewModel.getLoadStatus().observe(viewLifecycleOwner) {
                progressBar?.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        if (context != null) {
            listViewModel.getContactsList(context, "")
        }

        return listBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _listBinding = null
        progressBar = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.search_contact)
        val searchView = menuItem.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(query: String?): Boolean {
        val context = context
        if (context != null && query != null) {
            listViewModel.getContactsList(context, query)
        }

        return true
    }
}
