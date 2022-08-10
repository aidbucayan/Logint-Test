package com.adrian.bucayan.logintest.data.remote.request

/**
 * @author Adrian Bucayan
 */
data class UserRequest(
    var id: Int?,
    var email: String?,
    var password: String?,
    var name: String? = null,
    var phone: String? = null,
    var username: String? = null,
    var website: String? = null,
)