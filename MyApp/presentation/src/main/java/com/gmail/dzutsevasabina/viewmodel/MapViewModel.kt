package com.gmail.dzutsevasabina.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.dzutsevasabina.entity.Location
import com.gmail.dzutsevasabina.interactor.AddressInteractor
import com.gmail.dzutsevasabina.interactor.LocationInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MapViewModel @Inject constructor(
    var addressModel: AddressInteractor,
    var locationModel: LocationInteractor
) : ViewModel() {

    private val location = MutableLiveData<Location>()
    private val disposable: CompositeDisposable = CompositeDisposable()

    fun getLocation(): LiveData<Location> {
        return location
    }

    fun fetchLocation(id: Int, latitude: Double, longitude: Double) {
        disposable
            .add(Single.fromCallable {
                val address = addressModel.getAddress(
                    latitude.toString(),
                    longitude.toString()
                ) ?: ""

                location.postValue(
                    Location(
                        id, latitude, longitude, address
                    )
                )
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    fun load() {
        disposable
            .add(Single.fromCallable {
                location.value?.let { locationModel.insertLocation(it) }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    fun fetchExistingLocation(id: Int) {
        disposable
            .add(Single.fromCallable {
                val locationFromRepo = locationModel.getLocation(id)
                if (locationFromRepo != null) {
                    location.postValue(locationFromRepo)
                } else {
                    location.postValue(Location(id, 0.0, 0.0, ""))
                }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
