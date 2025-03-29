package com.example.paymentsimulator.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.paymentsimulator.ui.screens.history.HistoryScreen
import com.example.paymentsimulator.ui.screens.payment.PaymentScreen
import com.example.paymentsimulator.ui.screens.result.ResultScreen

@Composable
fun PaymentNavGraph(
    navController: NavHostController,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Payment.route
    ) {
        composable(route = Screens.Payment.route) {
            PaymentScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(route = Screens.Result.route) {
            ResultScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(route = Screens.History.route) {
            HistoryScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

sealed class Screens(val route: String) {
    object Payment : Screens("payment")
    object Result : Screens("result")
    object History : Screens("history")
}