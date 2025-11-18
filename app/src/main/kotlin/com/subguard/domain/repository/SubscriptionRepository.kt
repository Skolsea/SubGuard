package com.subguard.domain.repository

import com.subguard.domain.model.Subscription
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {

    fun getSubscriptions(): Flow<List<Subscription>>

    suspend fun getSubscriptionById(id: Int): Subscription?

    suspend fun insertSubscription(subscription: Subscription)

    suspend fun deleteSubscription(subscription: Subscription)
}
