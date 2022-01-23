package com.gmail.dzutsevasabina.interactor

interface AddressInteractor {
    fun getAddress(latitude: String, longitude: String) : String?
}
