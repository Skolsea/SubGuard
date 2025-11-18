package com.subguard.util

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val subscriptionName = inputData.getString(KEY_SUBSCRIPTION_NAME)
        val isTrial = inputData.getBoolean(KEY_IS_TRIAL, false)
        val daysBefore = inputData.getInt(KEY_DAYS_BEFORE, 1)

        // TODO: Implement actual notification logic here
        // For now, we just log the intent to notify
        println("NotificationWorker: Preparing to notify for $subscriptionName. Trial: $isTrial, Days Before: $daysBefore")

        return Result.success()
    }

    companion object {
        const val KEY_SUBSCRIPTION_NAME = "subscription_name"
        const val KEY_IS_TRIAL = "is_trial"
        const val KEY_DAYS_BEFORE = "days_before"
    }
}
