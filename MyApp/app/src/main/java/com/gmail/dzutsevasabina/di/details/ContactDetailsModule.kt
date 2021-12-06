package com.gmail.dzutsevasabina.di.details

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.gmail.dzutsevasabina.di.scopes.ContactDetailsScope
import com.gmail.dzutsevasabina.viewmodel.ContactDetailsViewModel
import dagger.Module
import dagger.Provides

@Module
class ContactDetailsModule {

    @ContactDetailsScope
    @Provides
    fun provideContactDetailsViewModel(context: Context): ContactDetailsViewModel? {
        if (context is ViewModelStoreOwner) {
            return ViewModelProvider(context).get(ContactDetailsViewModel::class.java)
        }
        return null
    }
}
