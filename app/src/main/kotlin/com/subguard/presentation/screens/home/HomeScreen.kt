package com.subguard.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.subguard.R
import com.subguard.domain.usecase.SubscriptionWithNextPayment
import com.subguard.presentation.navigation.Screen
import com.subguard.presentation.theme.NeonTeal
import com.subguard.presentation.theme.SoftRed
import com.subguard.presentation.viewmodel.HomeViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.ADD_EDIT) },
                containerColor = NeonTeal,
                contentColor = Color.Black,
                shape = MaterialTheme.shapes.large
            ) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_subscription))
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.total_monthly),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                IconButton(onClick = { navController.navigate(Screen.SETTINGS) }) {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = stringResource(R.string.settings),
                        tint = NeonTeal
                    )
                }
            }

            // Total Monthly Value
            Text(
                text = formatCurrency(state.totalMonthly, "$"), // Placeholder currency
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = NeonTeal,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))

            Spacer(modifier = Modifier.height(16.dp))

            // List of Subscriptions
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.subscriptions) { subWithPayment ->
                    SubscriptionItem(
                        subWithPayment = subWithPayment,
                        onClick = {
                            navController.navigate("${Screen.ADD_EDIT}?subscriptionId=${subWithPayment.subscription.id}")
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionItem(
    subWithPayment: SubscriptionWithNextPayment,
    onClick: () -> Unit
) {
    val subscription = subWithPayment.subscription
    val isTrial = subscription.isTrial
    val daysRemaining = subWithPayment.daysUntilPayment

    val borderColor = if (isTrial) SoftRed else NeonTeal
    val borderAlpha = if (isTrial) 0.8f else 0.5f

    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(2.dp) // Padding para simular el borde
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.large)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = subscription.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Text(
                        text = formatCurrency(subscription.price, subscription.currency),
                        style = MaterialTheme.typography.titleLarge,
                        color = if (isTrial) SoftRed else NeonTeal
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isTrial) {
                        stringResource(R.string.trial_ends_in, daysRemaining.toInt())
                    } else {
                        stringResource(R.string.next_payment_in, daysRemaining.toInt())
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun formatCurrency(amount: Double, currencySymbol: String): String {
    val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
    format.currency = java.util.Currency.getInstance(if (currencySymbol == "$") "USD" else "EUR") // Simplificación
    return format.format(amount)
}
