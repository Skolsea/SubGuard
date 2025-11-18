package com.subguard.domain.usecase

import com.subguard.domain.model.BillingCycle
import com.subguard.domain.model.Subscription
import com.subguard.domain.repository.SubscriptionRepository
import com.subguard.util.calculateNextBillingDate
import com.subguard.util.daysUntil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

data class SubscriptionWithNextPayment(
    val subscription: Subscription,
    val nextPaymentDate: LocalDate,
    val daysUntilPayment: Long
)

class GetSubscriptions @Inject constructor(
    private val repository: SubscriptionRepository
) {
    operator fun invoke(): Flow<List<SubscriptionWithNextPayment>> {
        return repository.getSubscriptions().map { subscriptions ->
            subscriptions.map { subscription ->
                val today = LocalDate.now()
                val nextPaymentDate = if (subscription.isTrial) {
                    subscription.trialEndDate ?: today
                } else {
                    calculateNextBillingDate(subscription.startDate, subscription.billingCycle, today)
                }

                val daysUntilPayment = today.daysUntil(nextPaymentDate)

                SubscriptionWithNextPayment(
                    subscription = subscription,
                    nextPaymentDate = nextPaymentDate,
                    daysUntilPayment = daysUntilPayment
                )
            }.sortedBy { it.daysUntilPayment } // Requisito: Ordenar por días restantes
        }
    }
}
