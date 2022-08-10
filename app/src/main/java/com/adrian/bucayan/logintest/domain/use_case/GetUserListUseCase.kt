package com.adrian.bucayan.logintest.domain.use_case

import com.adrian.bucayan.logintest.common.Constants
import com.adrian.bucayan.logintest.common.Resource
import com.adrian.bucayan.logintest.data.remote.dto.toUser
import com.adrian.bucayan.logintest.domain.models.User
import com.adrian.bucayan.logintest.domain.repository.LoginTestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(private val repository: LoginTestRepository) {

    operator fun invoke(): Flow<Resource<List<User>>> = flow {
        try {
            emit(Resource.Loading<List<User>>())
            val getUserList = repository.getUserListFromApi().map{ it.toUser() }
            emit(Resource.Success<List<User>>(getUserList))
        } catch(e: HttpException) {
            emit(Resource.Error<List<User>>(e.message?: Constants.ERROR_FROM_SERVER))
        } catch(e: IOException) {
            emit(Resource.Error<List<User>>(e.message?: Constants.EXCEPTION_ERROR))
        }
    }


}