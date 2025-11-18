package com.subguard.data.repository

import com.subguard.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

// NOTA: En un proyecto real, esto se implementaría con DataStore o SharedPreferences.
// Usamos MutableStateFlow para simular la persistencia de forma sencilla para el MVP.
@Singleton
class PreferenceRepositoryImpl @Inject constructor() : PreferenceRepository {

    private val _defaultCurrency = MutableStateFlow("$")
    private val _language = MutableStateFlow("en")

    override fun getDefaultCurrency(): Flow<String> = _defaultCurrency

    override suspend fun setDefaultCurrency(currency: String) {
        _defaultCurrency.update { currency }
    }

    override fun getLanguage(): Flow<String> = _language

    override suspend fun setLanguage(languageCode: String) {
        _language.update { languageCode }
    }
}
