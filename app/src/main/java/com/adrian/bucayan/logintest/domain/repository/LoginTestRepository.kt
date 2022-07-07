package com.adrian.bucayan.logintest.domain.repository

import com.adrian.bucayan.logintest.data.remote.dto.UserDto
import com.adrian.bucayan.logintest.domain.request.UserRequest


interface LoginTestRepository {

    suspend fun saveUser(request: UserRequest)

    suspend fun getUserList() : List<UserDto>
}