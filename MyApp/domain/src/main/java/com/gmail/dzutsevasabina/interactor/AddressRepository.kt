package com.gmail.dzutsevasabina.interactor

interface AddressRepository {
    fun getAddress(latitude: String, longitude: String): String?
}
