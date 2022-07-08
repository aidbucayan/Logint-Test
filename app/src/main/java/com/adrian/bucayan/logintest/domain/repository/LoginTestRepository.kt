package com.adrian.bucayan.logintest.domain.repository

import com.adrian.bucayan.logintest.data.datasource.model.EntityCacheUser
import com.adrian.bucayan.logintest.data.remote.dto.UserDto
import com.adrian.bucayan.logintest.domain.request.UserRequest


interface LoginTestRepository {

    suspend fun saveUser(request: UserRequest)

    suspend fun getUserListFromApi() : List<UserDto>

    suspend fun getUserByUserName(username: String) : EntityCacheUser

    suspend fun getUserByUserAndPasswordName(username: String, password: String) : EntityCacheUser
}