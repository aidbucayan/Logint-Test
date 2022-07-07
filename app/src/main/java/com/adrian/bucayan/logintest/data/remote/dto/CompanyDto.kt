package com.adrian.bucayan.logintest.data.remote.dto

import com.adrian.bucayan.logintest.domain.model.Company
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CompanyDto(

    @JsonProperty("bs")
    var bs: String? = null,

    @JsonProperty("catchPhrase")
    var catchPhrase: String? = null,

    @JsonProperty("name")
    var name: String? = null,

)

fun CompanyDto.toCompany(): Company {
    return Company (
        bs = bs,
        catchPhrase = catchPhrase,
        name = name,
    )
}
