package com.subguard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subguard.domain.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    val defaultCurrency: StateFlow<String> = preferenceRepository.getDefaultCurrency()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "$")

    val language: StateFlow<String> = preferenceRepository.getLanguage()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "en")

    fun setDefaultCurrency(currency: String) {
        viewModelScope.launch {
            preferenceRepository.setDefaultCurrency(currency)
        }
    }

    fun setLanguage(languageCode: String) {
        viewModelScope.launch {
            preferenceRepository.setLanguage(languageCode)
        }
    }
}
