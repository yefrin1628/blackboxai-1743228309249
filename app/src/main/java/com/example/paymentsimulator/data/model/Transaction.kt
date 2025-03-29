package com.example.paymentsimulator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val customerName: String,
    val amount: Double,
    val currency: String,
    val cardLastFour: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isSuccess: Boolean,
    val errorMessage: String? = null
)