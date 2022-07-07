package com.adrian.bucayan.logintest.domain.use_case

import com.adrian.bucayan.logintest.common.Resource
import com.adrian.bucayan.logintest.domain.repository.LoginTestRepository
import com.adrian.bucayan.logintest.domain.request.UserRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: LoginTestRepository) {

    operator fun invoke(request: UserRequest): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading<Unit>())
            val saveUser = repository.saveUser(request)
            emit(Resource.Success<Unit>(saveUser))
        } catch (e: Exception) {
            emit(Resource.Error<Unit>(e.message.toString()))
        }
    }

}