package com.example.paymentsimulator

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.paymentsimulator.navigation.PaymentNavGraph
import com.example.paymentsimulator.ui.theme.PaymentSimulatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Composable
fun PaymentApp() {
    PaymentSimulatorTheme {
        val navController = rememberNavController()
        PaymentNavGraph(
            navController = navController
        )
    }
}