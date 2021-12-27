package com.gmail.dzutsevasabina.di.list

import com.gmail.dzutsevasabina.di.IContactsListComponent
import com.gmail.dzutsevasabina.di.scopes.ContactsListScope
import com.gmail.dzutsevasabina.view.fragment.ContactListFragment
import dagger.Subcomponent

@ContactsListScope
@Subcomponent(modules = [ContactsListModule::class])
interface ContactsListComponent : IContactsListComponent {
    override fun inject(fragment: ContactListFragment)
}
