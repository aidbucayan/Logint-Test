package com.adrian.bucayan.logintest.data.datasource.cache

import androidx.room.*
import com.adrian.bucayan.logintest.data.datasource.model.EntityCacheUser

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUserName(username: String): EntityCacheUser

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: EntityCacheUser)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    fun getUserByUsernamePassword(username: String, password:String): EntityCacheUser

}