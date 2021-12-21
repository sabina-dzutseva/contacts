package com.gmail.dzutsevasabina.di

import com.gmail.dzutsevasabina.viewmodel.ContactDetailsViewModel
import com.gmail.dzutsevasabina.viewmodel.ContactListViewModel

interface IViewModelComponent {
    fun inject(viewmodel: ContactDetailsViewModel?)
    fun inject(viewmodel: ContactListViewModel?)
}
