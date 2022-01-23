package com.gmail.dzutsevasabina.di.app

import com.gmail.dzutsevasabina.di.IAppComponent
import com.gmail.dzutsevasabina.di.components.ContactDetailsComponent
import com.gmail.dzutsevasabina.di.components.ContactsListComponent
import com.gmail.dzutsevasabina.di.components.MapComponent
import com.gmail.dzutsevasabina.di.components.RouteComponent
import com.gmail.dzutsevasabina.di.viewmodel.*
import com.gmail.dzutsevasabina.di.viewmodel.details.AlarmServiceModule
import com.gmail.dzutsevasabina.di.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent : IAppComponent {
    override fun plusContactsListComponent(): ContactsListComponent
    override fun plusContactDetailsComponent(): ContactDetailsComponent
    override fun plusMapComponent(): MapComponent
    override fun plusRouteComponent(): RouteComponent
}
