package com.anesoft.anno1800influencecalculator.di

import android.content.Context
import androidx.room.Room
import com.anesoft.anno1800influencecalculator.repository.local.AppDatabase
import com.anesoft.anno1800influencecalculator.repository.local.dao.PlayerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun providePlayerDao(appAppDatabase: AppDatabase) : PlayerDao{
        return appAppDatabase.playerDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "RssReader"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}