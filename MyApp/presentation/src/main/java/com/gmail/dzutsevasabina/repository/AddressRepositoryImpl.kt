package com.gmail.dzutsevasabina.repository

import com.gmail.dzutsevasabina.interactor.AddressRepository
import com.gmail.dzutsevasabina.network.AddressService
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(var addressService: AddressService) :
    AddressRepository {
    override fun getAddress(latitude: String, longitude: String): String? =
        addressService.getAddress("$longitude, $latitude").execute()
            .body()?.response?.geoObjectCollection?.featureMember?.get(0)?.geoObject?.metaData?.geocoderMetaData?.address?.formattedAddress
}
