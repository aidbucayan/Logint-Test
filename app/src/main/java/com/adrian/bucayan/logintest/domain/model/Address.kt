package com.adrian.bucayan.logintest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    var city: String?,
    var geo: Geo?,
    var street: String?,
    var suite: String?,
    var zipcode: String?
): Parcelable