package com.gmail.dzutsevasabina.model

data class BriefContact(
    val id: String,
    val image: String,
    val name: String,
    val phoneNumber1: String,
)

data class DetailedContact(
    val id: String,
    val image: String,
    val name: String,
    val phoneNumber1: String,
    val phoneNumber2: String,
    val email1: String,
    val email2: String,
    val birthday: String,
    val description: String,
    var sendBirthdayNotifications: Boolean = false
)
