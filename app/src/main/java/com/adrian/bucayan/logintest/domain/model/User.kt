package com.adrian.bucayan.logintest.domain.model

data class User(
    var password: String? = null,
    var email: String? = null,
    var name: String? = null,
    var phone: String? = null,
    var username: String? = null,
    var website: String? = null,
    var id: Int? = null,
    var address: Address? = null,
    var company: Company? = null,
)