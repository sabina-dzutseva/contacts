package com.gmail.dzutsevasabina.di.components

import com.gmail.dzutsevasabina.di.IMapComponent
import com.gmail.dzutsevasabina.di.scopes.MapScope
import com.gmail.dzutsevasabina.di.viewmodel.*
import com.gmail.dzutsevasabina.di.viewmodel.details.AlarmServiceModule
import com.gmail.dzutsevasabina.view.fragment.MapFragment
import dagger.Subcomponent

@MapScope
@Subcomponent(modules = [ViewModelModule::class, AlarmServiceModule::class, AlarmHandlerModule::class,
    ContactInteractorModule::class, AddressInteractorModule::class, RepositoryModule::class,
    DatabaseModule::class, LocationInteractorModule::class, RouteInteractorModule::class])
interface MapComponent : IMapComponent {
    override fun inject(fragment: MapFragment)
}
