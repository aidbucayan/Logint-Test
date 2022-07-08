package com.adrian.bucayan.logintest.data.repository

import com.adrian.bucayan.logintest.data.datasource.cache.UserDao
import com.adrian.bucayan.logintest.data.datasource.mapper.UserMapper
import com.adrian.bucayan.logintest.data.datasource.model.EntityCacheUser
import com.adrian.bucayan.logintest.data.remote.LoginTestApi
import com.adrian.bucayan.logintest.data.remote.dto.UserDto
import com.adrian.bucayan.logintest.domain.repository.LoginTestRepository
import com.adrian.bucayan.logintest.domain.request.UserRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginTestRepositoryImpl @Inject constructor(
    private val api: LoginTestApi,
    private val userDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LoginTestRepository {

    override suspend fun saveUser(request: UserRequest) {
        withContext(ioDispatcher) {
            val userEntity = UserMapper.mapFromRequestToEntity(request)
            userDao.insertUser(userEntity)
        }
    }

    override suspend fun getUserListFromApi(): List<UserDto> {
        return api.getUsers()
    }

    override suspend fun getUserByUserName(username: String) : EntityCacheUser {
        val userEntity : EntityCacheUser
        withContext(ioDispatcher) {
            userEntity = userDao.getUserByUserName(username)
        }
        return userEntity
    }

    override suspend fun getUserByUserAndPasswordName(
        username: String,
        password: String
    ): EntityCacheUser {
        val userEntity : EntityCacheUser
        withContext(ioDispatcher) {
            userEntity = userDao.getUserByUsernamePassword(username, password)
        }
        return userEntity
    }

}