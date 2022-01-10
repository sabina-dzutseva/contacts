package com.gmail.dzutsevasabina.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.dzutsevasabina.R
import com.gmail.dzutsevasabina.databinding.FragmentListBinding
import com.gmail.dzutsevasabina.di.IApp
import com.gmail.dzutsevasabina.view.*
import com.gmail.dzutsevasabina.viewmodel.ContactListViewModel
import com.gmail.dzutsevasabina.viewmodel.ViewModelFactory
import javax.inject.Inject

const val OFFSET = 5

class ContactListFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _listBinding: FragmentListBinding? = null
    private val listBinding get() = _listBinding!!

    lateinit var viewModelFactory: ViewModelFactory
    @Inject set

    var listViewModel: ContactListViewModel? = null

    private var progressBar: ProgressBar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val appComp = (activity?.application as IApp).getAppComponent(context)

        val listComponent = appComp?.plusContactsListComponent()
        listComponent?.inject(this)

        listViewModel = ViewModelProvider(this, viewModelFactory)[ContactListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        val context = context
        _listBinding = FragmentListBinding.inflate(inflater, container, false)

        listBinding.mapButton.setOnClickListener { onClick(it) }

        if (context is AppCompatActivity) {
            context.supportActionBar?.title = getString(R.string.list_fragment_title)

            val adapter by lazy {
                ContactAdapter { pos: Int ->
                    val id = listViewModel?.getContactId(pos)
                    if (id != null) {
                        context.supportFragmentManager.setFragmentResult(
                            DETAILS_CONTACT_ID_REQUEST_KEY,
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

            listViewModel?.getList()?.observe(viewLifecycleOwner) {
                if (it != null) {
                    adapter.submitList(it)
                }

                listBinding.mapButton.isVisible = it.isNotEmpty()
            }

            progressBar = listBinding.progressBarList

            listViewModel?.getLoadStatus()?.observe(context) {
                progressBar?.isVisible = it
            }
        }

        if (context != null) {
            listViewModel?.getContactsList("")
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
            listViewModel?.getContactsList(query)
        }

        return true
    }

    private fun onClick(view: View?) {
        if (view is ImageButton) {
            activity?.supportFragmentManager?.setFragmentResult(
                ROUTE_ID_REQUEST_KEY,
                bundleOf(ID_KEY to id)
            )
        }
    }
}
