package com.gmail.dzutsevasabina.di

interface IAppComponent {
    fun plusContactsListComponent(): IContactsListComponent
    fun plusContactDetailsComponent(): IContactDetailsComponent
    fun plusViewModelComponent(): IViewModelComponent
}
