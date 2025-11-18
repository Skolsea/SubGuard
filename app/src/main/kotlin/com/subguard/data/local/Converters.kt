package com.subguard.data.local

import androidx.room.TypeConverter
import com.subguard.domain.model.BillingCycle
import com.subguard.domain.model.NotificationInterval
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

    @TypeConverter
    fun fromBillingCycle(value: BillingCycle): String {
        return value.name
    }

    @TypeConverter
    fun toBillingCycle(value: String): BillingCycle {
        return BillingCycle.valueOf(value)
    }

    @TypeConverter
    fun fromNotificationInterval(value: NotificationInterval): String {
        return value.name
    }

    @TypeConverter
    fun toNotificationInterval(value: String): NotificationInterval {
        return NotificationInterval.valueOf(value)
    }
}
