package com.adrian.bucayan.logintest.data.remote.dto

import com.adrian.bucayan.logintest.domain.models.User
import com.fasterxml.jackson.annotation.JsonProperty

data class UserDto(

    @JsonProperty("id")
    var id : Int? = null,

    @JsonProperty("address")
    var address : AddressDto? = null,

    @JsonProperty("company")
    var company: CompanyDto? = null,

    @JsonProperty("email")
    var email: String? = null,

    @JsonProperty("phone")
    var phone: String? = null,

    @JsonProperty("username")
    var username: String? = null,

    @JsonProperty("website")
    var website: String? = null,

    @JsonProperty("name")
    var name: String? = null,
)

fun UserDto.toUser(): User {
    return User(
        id = id,
        address = address?.toAddress(),
        company = company?.toCompany(),
        email = email,
        phone = phone,
        username = username,
        website = website,
        name = name
    )
}

