package com.gmail.dzutsevasabina.interfaces

import com.gmail.dzutsevasabina.ContactService

interface ServiceBinder {
    fun getService(): ContactService?
}
