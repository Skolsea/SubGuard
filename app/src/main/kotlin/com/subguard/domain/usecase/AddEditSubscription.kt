package com.subguard.domain.usecase

import com.subguard.domain.model.Subscription
import com.subguard.domain.repository.SubscriptionRepository
import javax.inject.Inject

class AddEditSubscription @Inject constructor(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(subscription: Subscription) {
        if (subscription.name.isBlank()) {
            throw InvalidSubscriptionException("The name of the subscription can't be empty.")
        }
        repository.insertSubscription(subscription)
    }
}

class InvalidSubscriptionException(message: String) : Exception(message)
