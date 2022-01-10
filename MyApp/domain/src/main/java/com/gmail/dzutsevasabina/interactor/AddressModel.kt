package com.gmail.dzutsevasabina.interactor

class AddressModel(private val locationRepository: AddressRepository) : AddressInteractor {
    override fun getAddress(latitude: String, longitude: String): String? {
        return locationRepository.getAddress(latitude, longitude)
    }
}
