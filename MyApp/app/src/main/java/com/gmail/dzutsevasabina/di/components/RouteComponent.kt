package com.gmail.dzutsevasabina.di.components

import com.gmail.dzutsevasabina.di.IRouteComponent
import com.gmail.dzutsevasabina.di.scopes.RouteScope
import com.gmail.dzutsevasabina.di.viewmodel.AddressInteractorModule
import com.gmail.dzutsevasabina.di.viewmodel.AlarmHandlerModule
import com.gmail.dzutsevasabina.di.viewmodel.ContactInteractorModule
import com.gmail.dzutsevasabina.di.viewmodel.DatabaseModule
import com.gmail.dzutsevasabina.di.viewmodel.LocationInteractorModule
import com.gmail.dzutsevasabina.di.viewmodel.RouteInteractorModule
import com.gmail.dzutsevasabina.di.viewmodel.ViewModelModule
import com.gmail.dzutsevasabina.di.viewmodel.details.AlarmServiceModule
import com.gmail.dzutsevasabina.view.fragment.RouteFragment
import dagger.Subcomponent

@RouteScope
@Subcomponent(
    modules = [
        ViewModelModule::class,
        AlarmServiceModule::class,
        AlarmHandlerModule::class,
        ContactInteractorModule::class,
        AddressInteractorModule::class,
        DatabaseModule::class,
        LocationInteractorModule::class,
        RouteInteractorModule::class
    ]
)
interface RouteComponent : IRouteComponent {
    override fun inject(fragment: RouteFragment)
}
