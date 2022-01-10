package com.gmail.dzutsevasabina.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.dzutsevasabina.entity.Location
import com.gmail.dzutsevasabina.entity.Route
import com.gmail.dzutsevasabina.interactor.RouteInteractor
import com.gmail.dzutsevasabina.model.BriefContact
import com.gmail.dzutsevasabina.viewmodel.mapper.BriefContactMapper
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RouteViewModel @Inject constructor(var routeModel: RouteInteractor) : ViewModel() {

    private val contacts = MutableLiveData<List<BriefContact>>()
    private val locations = MutableLiveData<List<Location>>()
    private val origin = MutableLiveData<Location>()
    private val destination = MutableLiveData<Location>()
    private val disposable: CompositeDisposable = CompositeDisposable()
    private val mapper: BriefContactMapper = BriefContactMapper()
    private val polyLineList = MutableLiveData<List<LatLng>>()
    private val isPolyLineOk = MutableLiveData<Boolean>()

    fun getContacts(): LiveData<List<BriefContact>> {
        return contacts
    }

    fun getLocations(): LiveData<List<Location>> {
        return locations
    }

    fun getPolylines(): LiveData<List<LatLng>> {
        return polyLineList
    }

    fun isPolyLineOk(): LiveData<Boolean> {
        return isPolyLineOk
    }

    fun fetchContactList() {
        disposable
            .add(Single.fromCallable {
                val contactList = routeModel.getContactsWithLocation()
                contacts.postValue(contactList.map { mapper.transform(it) })
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    fun fetchLocationsList() {
        disposable
            .add(Single.fromCallable {
                locations.postValue(routeModel.getAllLocations())
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    fun fetchDirections() {
        var routes: List<Route>? = null
         disposable
            .add(
                Single.fromCallable {
                    val orig = origin.value
                    val dest = destination.value

                    if (orig != null && dest != null) {
                        routes = routeModel.getRoutes(
                            orig.latitude.toString(),
                            orig.longitude.toString(),
                            dest.latitude.toString(),
                            dest.longitude.toString()
                        )
                    }

                    if (routes.isNullOrEmpty()) {
                        isPolyLineOk.postValue(false)
                    } else {
                        val polyline = routes?.get(0)?.points

                        if (polyline != null) {
                            polyLineList.postValue(PolyUtil.decode(polyline))
                            isPolyLineOk.postValue(true)
                        }
                    }
                }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe())
    }

    fun setOrigin(id: Int) = setLocation(origin, id)

    fun setDestination(id: Int) = setLocation(destination, id)

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    private fun setLocation(location: MutableLiveData<Location>, id: Int) {
        disposable
            .add(Single.fromCallable {
                location.postValue(routeModel.getLocation(id))
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }
}
