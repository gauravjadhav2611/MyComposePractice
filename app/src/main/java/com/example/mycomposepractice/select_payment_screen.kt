package com.example.mycomposepractice.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mycomposepractice.model.PaymentState
import com.example.mycomposepractice.viewmodel.PaymentViewModel

@Composable
fun PreAuthFlowScreen(
    navController: NavHostController,

    amount: String,

    viewModel: PaymentViewModel = viewModel()

) {

    val paymentState by viewModel
        .paymentState
        .collectAsState()

    when (val state = paymentState) {

        is PaymentState.PaymentMethod -> {

            PaymentMethodScreen(
                navController,

                amount = amount,

                onCardClicked = {

                    viewModel.onCardSelected()

                }

            )

        }

        is PaymentState.InsertCard -> {

            InsertCardScreen(
                navController,

                amount = amount,

                remainingSeconds = state.remainingSeconds,

                onCardInserted = {

                    viewModel.onCardInserted()

                }

            )

        }

        is PaymentState.Authorizing -> {

            AuthorizingScreen(
                amount = amount
            )

        }

        is PaymentState.Success -> {

            SuccessScreen(

                amount = amount,

                authCode = state.authCode,

                transactionId = state.transactionId,

                onYes = {

//                    viewModel.reset()
                    navController.navigate("home") {
                        popUpTo("home") {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }

                },

                onNo = {

                    viewModel.reset()

                }

            )

        }

    }

}