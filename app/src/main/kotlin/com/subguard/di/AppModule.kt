package com.subguard.di

import android.app.Application
import android.content.Context
import com.subguard.data.local.dao.SubscriptionDao
import com.subguard.data.repository.PreferenceRepositoryImpl
import com.subguard.data.repository.SubscriptionRepositoryImpl
import com.subguard.domain.repository.PreferenceRepository
import com.subguard.domain.repository.SubscriptionRepository
import com.subguard.domain.usecase.AddEditSubscription
import com.subguard.domain.usecase.DeleteSubscription
import com.subguard.domain.usecase.GetSubscription
import com.subguard.domain.usecase.GetSubscriptions
import com.subguard.domain.usecase.SubscriptionUseCases
import com.subguard.util.NotificationScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSubscriptionRepository(dao: SubscriptionDao): SubscriptionRepository {
        return SubscriptionRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideSubscriptionUseCases(repository: SubscriptionRepository): SubscriptionUseCases {
        return SubscriptionUseCases(
            getSubscriptions = GetSubscriptions(repository),
            getSubscription = GetSubscription(repository),
            addEditSubscription = AddEditSubscription(repository),
            deleteSubscription = DeleteSubscription(repository)
        )
    }

    @Provides
    @Singleton
    fun providePreferenceRepository(): PreferenceRepository {
        return PreferenceRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideNotificationScheduler(application: Application): NotificationScheduler {
        return NotificationScheduler(application.applicationContext)
    }
}
