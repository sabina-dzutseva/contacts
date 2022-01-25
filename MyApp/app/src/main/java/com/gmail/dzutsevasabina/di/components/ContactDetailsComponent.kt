package com.gmail.dzutsevasabina.di.components

import com.gmail.dzutsevasabina.di.IContactDetailsComponent
import com.gmail.dzutsevasabina.di.scopes.ContactDetailsScope
import com.gmail.dzutsevasabina.di.viewmodel.AddressInteractorModule
import com.gmail.dzutsevasabina.di.viewmodel.AlarmHandlerModule
import com.gmail.dzutsevasabina.di.viewmodel.ContactInteractorModule
import com.gmail.dzutsevasabina.di.viewmodel.DatabaseModule
import com.gmail.dzutsevasabina.di.viewmodel.LocationInteractorModule
import com.gmail.dzutsevasabina.di.viewmodel.RouteInteractorModule
import com.gmail.dzutsevasabina.di.viewmodel.ViewModelModule
import com.gmail.dzutsevasabina.di.viewmodel.details.AlarmServiceModule
import com.gmail.dzutsevasabina.view.fragment.ContactDetailsFragment
import dagger.Subcomponent

@ContactDetailsScope
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
interface ContactDetailsComponent : IContactDetailsComponent {
    override fun inject(fragment: ContactDetailsFragment)
}
