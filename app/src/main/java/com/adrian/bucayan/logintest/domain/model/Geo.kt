package com.adrian.bucayan.logintest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Geo(
    var lat: String?,
    var lng: String?
): Parcelable