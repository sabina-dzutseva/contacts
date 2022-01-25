package com.gmail.dzutsevasabina.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.dzutsevasabina.viewmodel.ContactDetailsViewModel
import com.gmail.dzutsevasabina.viewmodel.ContactListViewModel
import com.gmail.dzutsevasabina.viewmodel.MapViewModel
import com.gmail.dzutsevasabina.viewmodel.RouteViewModel
import com.gmail.dzutsevasabina.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ContactListViewModel::class)
    abstract fun bindContactListViewModel(viewModel: ContactListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactDetailsViewModel::class)
    abstract fun bindContactDetailsViewModel(viewModel: ContactDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindMapViewModel(viewModel: MapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RouteViewModel::class)
    abstract fun bindRouteViewModel(viewModel: RouteViewModel): ViewModel
}
