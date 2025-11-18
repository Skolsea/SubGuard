package com.subguard.util

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.subguard.domain.model.Subscription
import com.subguard.domain.usecase.calculateNextBillingDate
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationScheduler @Inject constructor(
    private val context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    fun scheduleNotification(subscription: Subscription) {
        val daysBefore = subscription.notificationInterval.days
        if (daysBefore == 0) {
            cancelNotification(subscription.id)
            return
        }

        val today = LocalDate.now()
        val nextBillingDate = if (subscription.isTrial) {
            subscription.trialEndDate ?: return
        } else {
            calculateNextBillingDate(subscription.startDate, subscription.billingCycle, today)
        }

        val notificationDate = nextBillingDate.minusDays(daysBefore.toLong())

        // Only schedule if the notification date is in the future
        if (notificationDate.isBefore(today) || notificationDate.isEqual(today)) {
            // If the date is today or past, we don't schedule a one-time notification.
            // In a real app, you might want to check if the notification for today has already been sent.
            return
        }

        val notificationDateTime = LocalDateTime.of(notificationDate, LocalTime.of(9, 0)) // 9 AM
        val now = LocalDateTime.now()

        val initialDelay = Duration.between(now, notificationDateTime).toSeconds()

        if (initialDelay <= 0) {
            // Should not happen based on the check above, but as a safeguard
            return
        }

        val inputData = Data.Builder()
            .putString(NotificationWorker.KEY_SUBSCRIPTION_NAME, subscription.name)
            .putBoolean(NotificationWorker.KEY_IS_TRIAL, subscription.isTrial)
            .putInt(NotificationWorker.KEY_DAYS_BEFORE, daysBefore)
            .build()

        val notificationWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(initialDelay, TimeUnit.SECONDS)
            .setInputData(inputData)
            .addTag(subscription.id.toString())
            .build()

        workManager.enqueueUniqueWork(
            "sub_notification_${subscription.id}",
            ExistingWorkPolicy.REPLACE,
            notificationWorkRequest
        )
    }

    fun cancelNotification(subscriptionId: Int) {
        workManager.cancelUniqueWork("sub_notification_${subscriptionId}")
    }
}
