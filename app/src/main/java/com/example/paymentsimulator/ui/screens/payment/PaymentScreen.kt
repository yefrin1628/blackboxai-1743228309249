package com.example.paymentsimulator.ui.screens.payment

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.paymentsimulator.ui.viewmodel.PaymentViewModel

@Composable
fun PaymentScreen(navController: NavController, viewModel: PaymentViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Payment Form", style = MaterialTheme.typography.titleLarge)

        // Customer Name
        TextField(
            value = viewModel.customerName,
            onValueChange = { viewModel.customerName = it },
            label = { Text("Customer Name") }
        )

        // Amount
        TextField(
            value = viewModel.amount,
            onValueChange = { viewModel.amount = it },
            label = { Text("Amount") },
            placeholder = { Text("Enter amount") }
        )

        // Card Number
        TextField(
            value = viewModel.cardNumber,
            onValueChange = { viewModel.cardNumber = it },
            label = { Text("Card Number") },
            placeholder = { Text("16 digits") }
        )

        // Expiry Date
        TextField(
            value = viewModel.expiryDate,
            onValueChange = { viewModel.expiryDate = it },
            label = { Text("Expiry Date (MM/YY)") },
            placeholder = { Text("MM/YY") }
        )

        // CVV
        TextField(
            value = viewModel.cvv,
            onValueChange = { viewModel.cvv = it },
            label = { Text("CVV") },
            placeholder = { Text("3-4 digits") }
        )

        // Currency Selection
        // (Add a Spinner or Dropdown for currency selection)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.processPayment() }) {
            Text("Process Payment")
        }

        // Show loading or error messages based on uiState
        when (uiState) {
            is PaymentUiState.Loading -> CircularProgressIndicator()
            is PaymentUiState.Error -> {
                val errorMessage = (uiState as PaymentUiState.Error).message
                Text(errorMessage, color = MaterialTheme.colorScheme.error)
            }
            is PaymentUiState.Success -> {
                // Navigate to ResultScreen
                navController.navigate("result")
            }
            else -> {}
        }
    }
}