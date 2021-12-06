package com.gmail.dzutsevasabina.di.app

import com.gmail.dzutsevasabina.di.interfaces.IAppComponent
import com.gmail.dzutsevasabina.di.details.ContactDetailsComponent
import com.gmail.dzutsevasabina.di.list.ContactsListComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : IAppComponent {
    override fun plusContactsListComponent(): ContactsListComponent
    override fun plusContactDetailsComponent(): ContactDetailsComponent
}
