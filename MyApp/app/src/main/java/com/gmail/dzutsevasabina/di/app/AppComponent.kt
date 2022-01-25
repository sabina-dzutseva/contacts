package com.gmail.dzutsevasabina.di.app

import com.gmail.dzutsevasabina.di.IAppComponent
import com.gmail.dzutsevasabina.di.components.ContactDetailsComponent
import com.gmail.dzutsevasabina.di.components.ContactsListComponent
import com.gmail.dzutsevasabina.di.components.MapComponent
import com.gmail.dzutsevasabina.di.components.RouteComponent
import com.gmail.dzutsevasabina.di.viewmodel.NetworkModule
import com.gmail.dzutsevasabina.di.viewmodel.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    RepositoryModule::class])
interface AppComponent : IAppComponent {
    override fun plusContactsListComponent(): ContactsListComponent
    override fun plusContactDetailsComponent(): ContactDetailsComponent
    override fun plusMapComponent(): MapComponent
    override fun plusRouteComponent(): RouteComponent
}
