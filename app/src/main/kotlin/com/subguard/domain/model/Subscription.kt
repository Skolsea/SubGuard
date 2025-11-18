package com.subguard.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

enum class BillingCycle {
    WEEKLY,
    MONTHLY,
    YEARLY
}

enum class NotificationInterval(val days: Int) {
    NONE(0),
    ONE_DAY(1),
    THREE_DAYS(3)
}

@Entity(tableName = "subscriptions")
data class Subscription(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val currency: String,
    val billingCycle: BillingCycle,
    val startDate: LocalDate,
    val isTrial: Boolean = false,
    val trialEndDate: LocalDate? = null,
    val notificationInterval: NotificationInterval = NotificationInterval.NONE
)
