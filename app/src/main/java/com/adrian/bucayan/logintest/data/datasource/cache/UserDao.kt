package com.adrian.bucayan.logintest.data.datasource.cache

import androidx.lifecycle.LiveData
import androidx.room.*
import com.adrian.bucayan.logintest.data.datasource.model.EntityCacheUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getUsers(): LiveData<List<EntityCacheUser>>

    @Query("SELECT * FROM users WHERE email = :email")
    fun getNoteById(email: String): EntityCacheUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: EntityCacheUser)

    @Delete
    fun deleteUser(user: EntityCacheUser)

}