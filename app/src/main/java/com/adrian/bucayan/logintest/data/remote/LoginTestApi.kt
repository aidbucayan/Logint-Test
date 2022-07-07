package com.adrian.bucayan.logintest.data.remote

import com.adrian.bucayan.logintest.data.remote.dto.UserDto
import retrofit2.http.*

interface LoginTestApi {

    @GET("/users")
    suspend fun getUsers(): List<UserDto>

}