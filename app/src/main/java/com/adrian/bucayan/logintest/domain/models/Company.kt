package com.adrian.bucayan.logintest.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Company(
    var bs: String?,
    var catchPhrase: String?,
    var name: String?
): Parcelable