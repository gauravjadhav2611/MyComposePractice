package com.example.mycomposepractice.model

sealed class PaymentState {

    object PaymentMethod : PaymentState()

    data class InsertCard(
        val remainingSeconds: Int = 60
    ) : PaymentState()

    object Authorizing : PaymentState()

    data class Success(
        val authCode: String,
        val transactionId: String
    ) : PaymentState()
}