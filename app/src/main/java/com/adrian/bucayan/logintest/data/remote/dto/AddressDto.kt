package com.adrian.bucayan.logintest.data.remote.dto

import com.adrian.bucayan.logintest.domain.model.Address
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AddressDto(

    @JsonProperty("city")
    var city: String? = null,

    @JsonProperty("geo")
    var geo: GeoDto? = null,

    @JsonProperty("street")
    var street: String? = null,

    @JsonProperty("suite")
    var suite: String? = null,

    @JsonProperty("zipcode")
    var zipcode: String? = null
)

fun AddressDto.toAddress(): Address {
    return Address (
        city = city,
        geo = geo?.toGeo(),
        street = street,
        suite = suite,
        zipcode = zipcode
    )
}
