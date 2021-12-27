package com.gmail.dzutsevasabina.di.viewmodel

import com.gmail.dzutsevasabina.di.IViewModelComponent
import com.gmail.dzutsevasabina.di.viewmodel.details.AlarmHandlerModule
import com.gmail.dzutsevasabina.viewmodel.ContactDetailsViewModel
import com.gmail.dzutsevasabina.viewmodel.ContactListViewModel
import dagger.Subcomponent

@Subcomponent(modules = [AlarmHandlerModule::class, ContactInteractorModule::class, RepositoryModule::class])
interface ViewModelComponent : IViewModelComponent {
    override fun inject(viewmodel: ContactListViewModel?)
    override fun inject(viewmodel: ContactDetailsViewModel?)
}
