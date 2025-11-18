package com.subguard.domain.usecase

import com.subguard.domain.model.Subscription
import com.subguard.domain.repository.SubscriptionRepository
import javax.inject.Inject

class GetSubscription @Inject constructor(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(id: Int): Subscription? {
        return repository.getSubscriptionById(id)
    }
}
