package com.example.paymentsimulator.ui.screens.result

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.paymentsimulator.ui.viewmodel.PaymentViewModel

@Composable
fun ResultScreen(navController: NavController, viewModel: PaymentViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is PaymentUiState.Success -> {
                val transaction = (uiState as PaymentUiState.Success).transaction
                Text("Payment Successful!", style = MaterialTheme.typography.titleLarge)
                Text("Customer: ${transaction.customerName}")
                Text("Amount: ${transaction.amount} ${transaction.currency}")
                Text("Card Ending: ${transaction.cardLastFour}")
            }
            is PaymentUiState.Error -> {
                val errorMessage = (uiState as PaymentUiState.Error).message
                Text("Payment Failed", style = MaterialTheme.typography.titleLarge)
                Text("Error: $errorMessage", color = MaterialTheme.colorScheme.error)
            }
            else -> {
                Text("No payment processed yet.")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Payment")
        }
    }
}