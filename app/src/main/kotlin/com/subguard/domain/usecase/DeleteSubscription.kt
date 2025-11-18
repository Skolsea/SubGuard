package com.subguard.domain.usecase

import com.subguard.domain.model.Subscription
import com.subguard.domain.repository.SubscriptionRepository
import javax.inject.Inject

class DeleteSubscription @Inject constructor(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(subscription: Subscription) {
        repository.deleteSubscription(subscription)
    }
}
