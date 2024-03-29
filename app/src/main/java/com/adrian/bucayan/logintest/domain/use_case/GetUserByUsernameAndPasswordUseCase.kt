package com.adrian.bucayan.logintest.domain.use_case

import com.adrian.bucayan.logintest.common.Resource
import com.adrian.bucayan.logintest.data.datasource.mapper.UserMapper
import com.adrian.bucayan.logintest.domain.models.User
import com.adrian.bucayan.logintest.domain.repository.LoginTestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserByUsernameAndPasswordUseCase @Inject constructor(
    private val repository: LoginTestRepository) {

    operator fun invoke(username: String, password: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            val entityCacheUser = repository.getUserByUserAndPasswordName(username, password)
            emit(Resource.Success<User>(UserMapper.mapFromUserToEntityCacheUser(entityCacheUser)))
        } catch (e: Exception) {
            emit(Resource.Error<User>("Incorrect"))
        }
    }

}