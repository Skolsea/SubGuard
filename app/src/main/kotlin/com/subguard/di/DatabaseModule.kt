package com.subguard.di

import android.app.Application
import androidx.room.Room
import com.subguard.data.local.SubscriptionDatabase
import com.subguard.data.local.SubscriptionDatabase.Companion.DATABASE_NAME
import com.subguard.data.local.dao.SubscriptionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSubscriptionDatabase(app: Application): SubscriptionDatabase {
        return Room.databaseBuilder(
            app,
            SubscriptionDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideSubscriptionDao(db: SubscriptionDatabase): SubscriptionDao {
        return db.subscriptionDao
    }
}
