package com.subguard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subguard.domain.usecase.SubscriptionUseCases
import com.subguard.domain.usecase.SubscriptionWithNextPayment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val subscriptionUseCases: SubscriptionUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        getSubscriptions()
    }

    private fun getSubscriptions() {
        subscriptionUseCases.getSubscriptions()
            .onEach { subscriptions ->
                val totalMonthly = subscriptions.sumOf {
                    if (it.subscription.isTrial) 0.0 else it.subscription.price
                }
                _state.value = state.value.copy(
                    subscriptions = subscriptions,
                    totalMonthly = totalMonthly
                )
            }.launchIn(viewModelScope)
    }

    // Eventos de la UI (e.g., para eliminar, aunque no se pide en el dashboard)
    // fun onEvent(event: HomeEvent) { ... }
}

data class HomeState(
    val subscriptions: List<SubscriptionWithNextPayment> = emptyList(),
    val totalMonthly: Double = 0.0
)
