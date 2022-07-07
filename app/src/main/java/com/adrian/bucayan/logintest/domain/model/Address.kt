package com.adrian.bucayan.logintest.domain.model

data class Address(
    var city: String?,
    var geo: Geo?,
    var street: String?,
    var suite: String?,
    var zipcode: String?
)