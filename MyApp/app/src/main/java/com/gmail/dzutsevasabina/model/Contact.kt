package com.gmail.dzutsevasabina.model

data class Contact(
    val image: Int,
    val name: String,
    val birthday: String,
    val phoneNumber1: String,
    val phoneNumber2: String,
    val email1: String,
    val email2: String,
    val description: String,
    var sendBirthdayNotifications: Boolean = false
)
