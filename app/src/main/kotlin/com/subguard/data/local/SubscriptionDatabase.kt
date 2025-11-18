package com.subguard.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.subguard.data.local.dao.SubscriptionDao
import com.subguard.domain.model.Subscription

@Database(
    entities = [Subscription::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SubscriptionDatabase : RoomDatabase() {
    abstract val subscriptionDao: SubscriptionDao

    companion object {
        const val DATABASE_NAME = "subguard_db"
    }
}
