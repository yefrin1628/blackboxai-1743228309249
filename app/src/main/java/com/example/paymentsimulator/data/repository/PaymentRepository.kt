package com.example.paymentsimulator.data.repository

import com.example.paymentsimulator.data.AppDatabase
import com.example.paymentsimulator.data.model.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaymentRepository @Inject constructor(
    private val database: AppDatabase
) {
    suspend fun saveTransaction(transaction: Transaction) {
        database.transactionDao().insertTransaction(transaction)
    }

    fun getAllTransactions(): Flow<List<Transaction>> {
        return database.transactionDao().getAllTransactions()
    }

    suspend fun clearTransactions() {
        database.transactionDao().clearAllTransactions()
    }
}