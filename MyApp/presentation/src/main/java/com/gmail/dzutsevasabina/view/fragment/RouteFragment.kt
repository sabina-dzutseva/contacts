package com.gmail.dzutsevasabina.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmail.dzutsevasabina.R
import com.gmail.dzutsevasabina.databinding.FragmentRouteBinding
import com.gmail.dzutsevasabina.di.IApp
import com.gmail.dzutsevasabina.model.BriefContact
import com.gmail.dzutsevasabina.viewmodel.RouteViewModel
import com.gmail.dzutsevasabina.viewmodel.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import javax.inject.Inject

class RouteFragment : Fragment() {

    private var _routeBinding: FragmentRouteBinding? = null
    private val routeBinding get() = _routeBinding!!

    lateinit var viewModelFactory: ViewModelFactory
        @Inject set

    var routeViewModel: RouteViewModel? = null

    private var polyline: Polyline? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val appComp = (activity?.application as IApp).getAppComponent(context)

        val routeComponent = appComp?.plusRouteComponent()
        routeComponent?.inject(this)

        routeViewModel = ViewModelProvider(this, viewModelFactory)[RouteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _routeBinding = FragmentRouteBinding.inflate(inflater, container, false)

        val context = context
        if (context is AppCompatActivity) {
            context.supportActionBar?.title = getString(R.string.route_fragment_title)

            routeViewModel?.getContacts()?.observe(viewLifecycleOwner) { contacts ->
                setSpinners(context, contacts)
            }
        }

        routeViewModel?.fetchContactList()

        routeBinding.cancelButton.setOnClickListener { polyline?.remove() }
        routeBinding.routeButton.setOnClickListener { routeViewModel?.fetchDirections() }

        routeViewModel?.isPolyLineOk()?.observe(viewLifecycleOwner) {
            if (!it) {
                Toast.makeText(
                    context,
                    getString(R.string.cannot_build_a_route),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        supportMapFragment.getMapAsync { map ->

            setAllMarkers(map)

            routeViewModel?.getPolylines()?.observe(viewLifecycleOwner) {
                val polylineOptions = PolylineOptions()
                if (context != null) {
                    polylineOptions.color(ContextCompat.getColor(context, R.color.brown))
                    polylineOptions.width(8F)
                    polylineOptions.jointType(JointType.ROUND)
                    polylineOptions.addAll(it)
                    polyline?.remove()
                    polyline = map.addPolyline(polylineOptions)

                    val builder = LatLngBounds.Builder()
                    polylineOptions.points.forEach {
                        builder.include(it)
                    }

                    val bounds = builder.build()
                    val padding = 50
                    val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                    map.animateCamera(cu)
                }
            }
        }

        return routeBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _routeBinding = null
        polyline = null
    }

    private fun setAllMarkers(map: GoogleMap) {
        val builder = LatLngBounds.Builder()

        routeViewModel?.getLocations()?.observe(viewLifecycleOwner) { locations ->
            val markerOptions = MarkerOptions()
            locations.forEach {
                val latlng = LatLng(it.latitude, it.longitude)
                markerOptions.position(latlng)
                builder.include(latlng)
                map.addMarker(markerOptions)
            }

            if (locations.isNotEmpty()) {
                val bounds = builder.build()
                val padding = 50
                val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                map.animateCamera(cu)
            }
        }

        routeViewModel?.fetchLocationsList()
    }

    private fun setSpinners(context: Context, contacts: List<BriefContact>) {
        val contactsWithLocationAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            contacts.map { contact -> contact.name })

        contactsWithLocationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val originSpinner = routeBinding.fromList
        originSpinner.adapter = contactsWithLocationAdapter

        originSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                routeViewModel?.setOrigin(contacts[position].id)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        val destinationSpinner = routeBinding.toList
        destinationSpinner.adapter = contactsWithLocationAdapter

        destinationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                routeViewModel?.setDestination(contacts[position].id)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }
}
