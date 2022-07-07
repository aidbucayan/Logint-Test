package com.adrian.bucayan.logintest.data.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adrian.bucayan.logintest.data.datasource.model.EntityCacheUser

@Database(
    entities = [EntityCacheUser::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase: RoomDatabase() {

    abstract val noteDao: UserDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}