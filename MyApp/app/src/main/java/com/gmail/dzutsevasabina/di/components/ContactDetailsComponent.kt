package com.gmail.dzutsevasabina.di.components

import com.gmail.dzutsevasabina.di.IContactDetailsComponent
import com.gmail.dzutsevasabina.di.scopes.ContactDetailsScope
import com.gmail.dzutsevasabina.di.viewmodel.*
import com.gmail.dzutsevasabina.di.viewmodel.details.AlarmServiceModule
import com.gmail.dzutsevasabina.view.fragment.ContactDetailsFragment
import dagger.Subcomponent

@ContactDetailsScope
@Subcomponent(modules = [ViewModelModule::class, AlarmServiceModule::class, AlarmHandlerModule::class,
    ContactInteractorModule::class, AddressInteractorModule::class, RepositoryModule::class,
    DatabaseModule::class, LocationInteractorModule::class, RouteInteractorModule::class])
interface ContactDetailsComponent : IContactDetailsComponent {
    override fun inject(fragment: ContactDetailsFragment)
}
