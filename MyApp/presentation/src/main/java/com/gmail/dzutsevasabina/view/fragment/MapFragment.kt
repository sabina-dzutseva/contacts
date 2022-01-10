package com.gmail.dzutsevasabina.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmail.dzutsevasabina.R
import com.gmail.dzutsevasabina.databinding.FragmentMapBinding
import com.gmail.dzutsevasabina.di.IApp
import com.gmail.dzutsevasabina.entity.Location
import com.gmail.dzutsevasabina.view.ARG_ID
import com.gmail.dzutsevasabina.viewmodel.MapViewModel
import com.gmail.dzutsevasabina.viewmodel.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class MapFragment : Fragment() {

    private var _mapBinding: FragmentMapBinding? = null
    private val mapBinding get() = _mapBinding!!

    lateinit var viewModelFactory: ViewModelFactory
    @Inject set

    var mapViewModel: MapViewModel? = null

    private var marker: Marker? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val appComp = (activity?.application as IApp).getAppComponent(context)

        val mapComponent = appComp?.plusMapComponent()
        mapComponent?.inject(this)

        mapViewModel = ViewModelProvider(this, viewModelFactory)[MapViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val id = requireArguments().getInt(ARG_ID)

        _mapBinding = FragmentMapBinding.inflate(inflater, container, false)

        val context = context
        if (context is AppCompatActivity) {
            context.supportActionBar?.title = getString(R.string.map_fragment_title)
        }

        mapBinding.saveButton.setOnClickListener { onClick(it) }
        mapBinding.saveButton.isEnabled = false

        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        supportMapFragment.getMapAsync { map ->
            mapViewModel?.fetchExistingLocation(id)
            mapViewModel?.getLocation()?.observe(viewLifecycleOwner) {
                updateViews(map, it)
            }

            map.setOnMapClickListener { latLng ->
                mapBinding.saveButton.isEnabled = true
                mapViewModel?.fetchLocation(id, latLng.latitude, latLng.longitude)
            }
        }

        return mapBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mapBinding = null
        marker = null
    }

    companion object {
        fun newInstance(id: Int) = MapFragment().apply {
            arguments = bundleOf(
                ARG_ID to id
            )
        }
    }

    private fun onClick(view: View?) {
        if (view is Button) {
            val id = arguments?.getInt(ARG_ID)
            if (id != null) {
                mapViewModel?.load()
                view.isEnabled = false
            }
        }
    }

    private fun updateViews(map: GoogleMap, location: Location) {
        val text: String = getString(R.string.address, location.address)

        if (location.address.isEmpty()) {
            map.clear()
            map.animateCamera(CameraUpdateFactory.zoomOut())
        } else {
            addMarker(map, location.longitude, location.latitude)
        }

        mapBinding.address.text =  String.format(text)
    }

    private fun addMarker(map: GoogleMap, longitude: Double, latitude: Double) {
            val markerOptions = MarkerOptions()
            val latlng = LatLng(latitude, longitude)
            markerOptions.position(latlng)
            markerOptions.title("$latitude : $longitude ")
            map.clear()
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15F))
            marker = map.addMarker(markerOptions)
    }
}
