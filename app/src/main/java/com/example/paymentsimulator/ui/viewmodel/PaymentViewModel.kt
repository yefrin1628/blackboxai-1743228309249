package com.example.paymentsimulator.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paymentsimulator.data.model.Transaction
import com.example.paymentsimulator.data.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val repository: PaymentRepository
) : ViewModel() {

    // UI State
    private val _uiState = MutableStateFlow<PaymentUiState>(PaymentUiState.Idle)
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    // Form fields
    var customerName = ""
    var amount = ""
    var cardNumber = ""
    var expiryDate = ""
    var cvv = ""
    var selectedCurrency = "USD"

    fun processPayment() {
        viewModelScope.launch {
            _uiState.value = PaymentUiState.Loading
            
            // Simulate network delay
            delay(2000)
            
            // Validate inputs
            val validationError = validateInputs()
            if (validationError != null) {
                _uiState.value = PaymentUiState.Error(validationError)
                return@launch
            }

            // Simulate payment processing (random success/failure)
            val isSuccess = Random().nextBoolean()
            val transaction = createTransaction(isSuccess)
            
            // Save transaction
            repository.saveTransaction(transaction)
            
            // Update UI state
            _uiState.value = if (isSuccess) {
                PaymentUiState.Success(transaction)
            } else {
                PaymentUiState.Error("Payment declined by bank")
            }
        }
    }

    private fun validateInputs(): String? {
        if (customerName.isBlank()) return "Customer name is required"
        if (amount.isBlank()) return "Amount is required"
        if (cardNumber.length != 16 || !cardNumber.matches(Regex("\\d+"))) {
            return "Invalid card number (must be 16 digits)"
        }
        if (!expiryDate.matches(Regex("\\d{2}/\\d{2}"))) {
            return "Invalid expiry date format (MM/YY)"
        }
        if (cvv.length !in 3..4 || !cvv.matches(Regex("\\d+"))) {
            return "Invalid CVV (must be 3-4 digits)"
        }
        return null
    }

    private fun createTransaction(isSuccess: Boolean): Transaction {
        return Transaction(
            customerName = customerName,
            amount = amount.toDouble(),
            currency = selectedCurrency,
            cardLastFour = cardNumber.takeLast(4),
            isSuccess = isSuccess,
            errorMessage = if (!isSuccess) "Payment declined" else null
        )
    }

    fun resetState() {
        _uiState.value = PaymentUiState.Idle
    }
}

sealed class PaymentUiState {
    object Idle : PaymentUiState()
    object Loading : PaymentUiState()
    data class Success(val transaction: Transaction) : PaymentUiState()
    data class Error(val message: String) : PaymentUiState()
}