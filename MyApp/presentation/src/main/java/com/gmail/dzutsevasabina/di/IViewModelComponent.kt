package com.gmail.dzutsevasabina.di

import com.gmail.dzutsevasabina.viewmodel.ContactDetailsViewModel
import com.gmail.dzutsevasabina.viewmodel.ContactListViewModel
import com.gmail.dzutsevasabina.viewmodel.MapViewModel
import com.gmail.dzutsevasabina.viewmodel.RouteViewModel

interface IViewModelComponent {
    fun inject(viewmodel: ContactDetailsViewModel?)
    fun inject(viewmodel: ContactListViewModel?)
    fun inject(viewmodel: MapViewModel?)
    fun inject(viewmodel: RouteViewModel?)
}
