package com.adrian.bucayan.logintest.data.datasource.mapper

import com.adrian.bucayan.logintest.data.datasource.model.EntityCacheUser
import com.adrian.bucayan.logintest.domain.model.Address
import com.adrian.bucayan.logintest.domain.model.Company
import com.adrian.bucayan.logintest.domain.request.UserRequest

class UserMapper {

    companion object {

        fun mapFromRequestToEntity(userRequest: UserRequest): EntityCacheUser {
            return EntityCacheUser(
                email = userRequest.email!!,
                password = userRequest.password!!,
                name = userRequest.name!!,
                phone = userRequest.phone!!,
                username = userRequest.username!!,
                website = userRequest.website!!,
            )
        }
    }

}