package com.subguard.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    fun getDefaultCurrency(): Flow<String>
    suspend fun setDefaultCurrency(currency: String)

    fun getLanguage(): Flow<String>
    suspend fun setLanguage(languageCode: String)
}
