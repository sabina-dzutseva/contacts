package com.gmail.dzutsevasabina.di.details

import com.gmail.dzutsevasabina.di.IContactDetailsComponent
import com.gmail.dzutsevasabina.di.scopes.ContactDetailsScope
import com.gmail.dzutsevasabina.view.fragment.ContactDetailsFragment
import dagger.Subcomponent

@ContactDetailsScope
@Subcomponent(modules = [ContactDetailsModule::class])
interface ContactDetailsComponent : IContactDetailsComponent {
    override fun inject(contactDetailsFragment: ContactDetailsFragment)
}
