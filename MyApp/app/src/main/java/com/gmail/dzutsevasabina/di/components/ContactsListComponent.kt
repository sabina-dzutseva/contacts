package com.gmail.dzutsevasabina.di.components

import com.gmail.dzutsevasabina.di.IContactsListComponent
import com.gmail.dzutsevasabina.di.scopes.ContactsListScope
import com.gmail.dzutsevasabina.di.viewmodel.*
import com.gmail.dzutsevasabina.di.viewmodel.details.AlarmServiceModule
import com.gmail.dzutsevasabina.view.fragment.ContactListFragment
import dagger.Subcomponent

@ContactsListScope
@Subcomponent(modules = [ViewModelModule::class, AlarmServiceModule::class, AlarmHandlerModule::class,
    ContactInteractorModule::class, AddressInteractorModule::class, RepositoryModule::class,
    DatabaseModule::class, LocationInteractorModule::class, RouteInteractorModule::class])
interface ContactsListComponent : IContactsListComponent {
    override fun inject(fragment: ContactListFragment)
}
