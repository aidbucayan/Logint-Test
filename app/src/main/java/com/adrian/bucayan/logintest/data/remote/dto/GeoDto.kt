package com.adrian.bucayan.logintest.data.remote.dto

import com.adrian.bucayan.logintest.domain.model.Geo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GeoDto(

    @JsonProperty("lat")
    var lat: String? = null,

    @JsonProperty("lng")
    var lng: String? = null,

)

fun GeoDto.toGeo(): Geo {
    return Geo (
        lat = lat,
        lng = lng,
    )
}
