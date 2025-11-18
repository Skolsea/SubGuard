package com.subguard.domain.usecase

import javax.inject.Inject

data class SubscriptionUseCases @Inject constructor(
    val getSubscriptions: GetSubscriptions,
    val getSubscription: GetSubscription,
    val addEditSubscription: AddEditSubscription,
    val deleteSubscription: DeleteSubscription
)
