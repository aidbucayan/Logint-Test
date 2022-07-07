package com.adrian.bucayan.logintest.di

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.adrian.bucayan.logintest.data.remote.LoginTestApi
import com.adrian.bucayan.logintest.data.repository.LoginTestRepositoryImpl
import com.adrian.bucayan.logintest.domain.repository.LoginTestRepository
import com.adrian.bucayan.logintest.BuildConfig
import com.adrian.bucayan.logintest.data.datasource.preference.AppPrefs
import com.adrian.bucayan.logintest.presentation.util.Utils
import com.adrian.bucayan.logintest.data.datasource.cache.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserDatabase(app: Application): UserDatabase {
        return Room.databaseBuilder(
            app,
            UserDatabase::class.java,
            UserDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMyRepository(api: LoginTestApi, userDatabase: UserDatabase): LoginTestRepository {
        return LoginTestRepositoryImpl(api, userDatabase.noteDao)
    }

    @Singleton
    @Provides
    fun providesTimberTree(): Timber.Tree {
        class ReportingTree : Timber.Tree() {
            override fun log(
                priority: Int,
                tag: String?,
                message: String,
                throwable: Throwable?)
            {
                if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                    return
                }
            }
        }

        return when(BuildConfig.DEBUG) {
            true -> Timber.DebugTree()
            else -> ReportingTree()
        }
    }

    @Singleton
    @Provides
    fun provideUtils(@ApplicationContext context: Context): Utils {
        return Utils(context)
    }

    @Singleton
    @Provides
    fun provideAppPrefs(@ApplicationContext context: Context): AppPrefs {
        return AppPrefs(context)
    }

}