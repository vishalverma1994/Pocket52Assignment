package com.employeecare.data.model

data class UserDataModel(
    var id: Int = 0,
    var name: String = "",
    var username: String = "",
    var email: String = "",
    var phone: String = "",
    var website: String = "",
    val address: Address,
    val company: Company
)

data class Address(
    var street: String = "",
    var suite: String = "",
    var city: String = "",
    var zipcode: String = "",
    val geo: GeoLocation
)

data class GeoLocation(
    var lat: String = "",
    var lng: String = ""
)

data class Company(
    var name: String = "",
    var catchPhrase: String = "",
    var bs: String = "",
)