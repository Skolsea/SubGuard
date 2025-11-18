package com.subguard.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subguard.domain.model.BillingCycle
import com.subguard.domain.model.NotificationInterval
import com.subguard.domain.model.Subscription
import com.subguard.domain.usecase.AddEditSubscription
import com.subguard.domain.usecase.InvalidSubscriptionException
import com.subguard.domain.usecase.SubscriptionUseCases
import com.subguard.util.NotificationScheduler
import com.subguard.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val notificationScheduler: NotificationScheduler,
    private val subscriptionUseCases: SubscriptionUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var name by mutableStateOf("")
        private set
    var price by mutableStateOf("")
        private set
    var currency by mutableStateOf("$") // Default
        private set
    var billingCycle by mutableStateOf(BillingCycle.MONTHLY)
        private set
    var startDate by mutableStateOf(LocalDate.now())
        private set
    var isTrial by mutableStateOf(false)
        private set
    var trialEndDate by mutableStateOf<LocalDate?>(null)
        private set
    var notificationInterval by mutableStateOf(NotificationInterval.ONE_DAY)
        private set

    private var currentSubscriptionId: Int? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        savedStateHandle.get<Int>("subscriptionId")?.let { subscriptionId ->
            if (subscriptionId != -1) {
                viewModelScope.launch {
                    subscriptionUseCases.getSubscription(subscriptionId)?.let { subscription ->
                        currentSubscriptionId = subscription.id
                        name = subscription.name
                        price = subscription.price.toString()
                        currency = subscription.currency
                        billingCycle = subscription.billingCycle
                        startDate = subscription.startDate
                        isTrial = subscription.isTrial
                        trialEndDate = subscription.trialEndDate
                        notificationInterval = subscription.notificationInterval
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.OnNameChange -> name = event.name
            is AddEditEvent.OnPriceChange -> price = event.price
            is AddEditEvent.OnCurrencyChange -> currency = event.currency
            is AddEditEvent.OnBillingCycleChange -> billingCycle = event.cycle
            is AddEditEvent.OnStartDateChange -> startDate = event.date
            is AddEditEvent.OnIsTrialChange -> {
                isTrial = event.isTrial
                if (!isTrial) trialEndDate = null
            }
            is AddEditEvent.OnTrialEndDateChange -> trialEndDate = event.date
            is AddEditEvent.OnNotificationIntervalChange -> notificationInterval = event.interval
            AddEditEvent.OnSaveSubscription -> {
                viewModelScope.launch {
                    try {
                        val newSubscription = Subscription(
                            id = currentSubscriptionId ?: 0,
                            name = name,
                            price = price.toDoubleOrNull() ?: 0.0,
                            currency = currency,
                            billingCycle = billingCycle,
                            startDate = startDate,
                            isTrial = isTrial,
                            trialEndDate = trialEndDate,
                            notificationInterval = notificationInterval
                        )
                        subscriptionUseCases.addEditSubscription(newSubscription)
                        notificationScheduler.scheduleNotification(newSubscription)
                            Subscription(
                                id = currentSubscriptionId ?: 0,
                                name = name,
                                price = price.toDoubleOrNull() ?: 0.0,
                                currency = currency,
                                billingCycle = billingCycle,
                                startDate = startDate,
                                isTrial = isTrial,
                                trialEndDate = trialEndDate,
                                notificationInterval = notificationInterval
                            )
                        )
                        _uiEvent.send(UiEvent.PopBackStack)
                    } catch (e: InvalidSubscriptionException) {
                        _uiEvent.send(UiEvent.ShowSnackbar(e.message ?: "Could not save subscription"))
                    } catch (e: Exception) {
                        _uiEvent.send(UiEvent.ShowSnackbar("An unexpected error occurred."))
                    }
                }
            }
        }
    }
}

sealed class AddEditEvent {
    data class OnNameChange(val name: String) : AddEditEvent()
    data class OnPriceChange(val price: String) : AddEditEvent()
    data class OnCurrencyChange(val currency: String) : AddEditEvent()
    data class OnBillingCycleChange(val cycle: BillingCycle) : AddEditEvent()
    data class OnStartDateChange(val date: LocalDate) : AddEditEvent()
    data class OnIsTrialChange(val isTrial: Boolean) : AddEditEvent()
    data class OnTrialEndDateChange(val date: LocalDate) : AddEditEvent()
    data class OnNotificationIntervalChange(val interval: NotificationInterval) : AddEditEvent()
    object OnSaveSubscription : AddEditEvent()
}

sealed class UiEvent {
    object PopBackStack : UiEvent()
    data class ShowSnackbar(val message: String) : UiEvent()
}
