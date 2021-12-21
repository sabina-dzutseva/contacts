package com.gmail.dzutsevasabina.di.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.gmail.dzutsevasabina.di.scopes.ContactsListScope
import com.gmail.dzutsevasabina.viewmodel.ContactListViewModel
import dagger.Module
import dagger.Provides

@Module
class ContactsListModule {
    @ContactsListScope
    @Provides
    fun provideContactsListViewModel(context: Context): ContactListViewModel? {
        if (context is ViewModelStoreOwner) {
            return ViewModelProvider(context).get(ContactListViewModel::class.java)
        }

        return null
    }
}
