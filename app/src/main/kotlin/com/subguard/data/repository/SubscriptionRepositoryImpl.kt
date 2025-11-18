package com.subguard.data.repository

import com.subguard.data.local.dao.SubscriptionDao
import com.subguard.domain.model.Subscription
import com.subguard.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscriptionRepositoryImpl @Inject constructor(
    private val dao: SubscriptionDao
) : SubscriptionRepository {

    override fun getSubscriptions(): Flow<List<Subscription>> {
        return dao.getAllSubscriptions()
    }

    override suspend fun getSubscriptionById(id: Int): Subscription? {
        return dao.getSubscriptionById(id)
    }

    override suspend fun insertSubscription(subscription: Subscription) {
        dao.insertSubscription(subscription)
    }

    override suspend fun deleteSubscription(subscription: Subscription) {
        dao.deleteSubscription(subscription)
    }
}
