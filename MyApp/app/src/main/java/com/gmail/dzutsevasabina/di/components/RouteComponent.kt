package com.gmail.dzutsevasabina.di.components

import com.gmail.dzutsevasabina.di.IRouteComponent
import com.gmail.dzutsevasabina.di.scopes.RouteScope
import com.gmail.dzutsevasabina.di.viewmodel.*
import com.gmail.dzutsevasabina.di.viewmodel.details.AlarmServiceModule
import com.gmail.dzutsevasabina.view.fragment.RouteFragment
import dagger.Subcomponent

@RouteScope
@Subcomponent(modules = [ViewModelModule::class, AlarmServiceModule::class, AlarmHandlerModule::class,
    ContactInteractorModule::class, AddressInteractorModule::class, RepositoryModule::class,
    DatabaseModule::class, LocationInteractorModule::class, RouteInteractorModule::class])
interface RouteComponent : IRouteComponent {
    override fun inject(fragment: RouteFragment)
}
