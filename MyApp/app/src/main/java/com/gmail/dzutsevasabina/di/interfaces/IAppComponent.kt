package com.gmail.dzutsevasabina.di.interfaces

import com.gmail.dzutsevasabina.di.interfaces.IContactDetailsComponent
import com.gmail.dzutsevasabina.di.interfaces.IContactsListComponent

interface IAppComponent {
    fun plusContactsListComponent(): IContactsListComponent
    fun plusContactDetailsComponent(): IContactDetailsComponent
}
