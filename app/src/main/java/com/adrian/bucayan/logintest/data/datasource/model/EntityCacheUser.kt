package com.adrian.bucayan.logintest.data.datasource.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "users")
data class EntityCacheUser(

    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "website") val website: String?,

)