package com.example.mycomposepractice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomposepractice.model.PaymentState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class PaymentViewModel : ViewModel() {

    private val _paymentState =
        MutableStateFlow<PaymentState>(
            PaymentState.PaymentMethod
        )

    val paymentState: StateFlow<PaymentState> = _paymentState

    fun onCardSelected() {

        viewModelScope.launch {

            for (i in 60 downTo 0) {

                _paymentState.value =
                    PaymentState.InsertCard(i)

                delay(1000)

                if (_paymentState.value !is PaymentState.InsertCard)
                    return@launch
            }

            _paymentState.value =
                PaymentState.PaymentMethod

        }

    }

    fun onCardInserted() {

        viewModelScope.launch {

            _paymentState.value =
                PaymentState.Authorizing

            delay(3000)

            _paymentState.value =
                PaymentState.Success(
                    authCode = randomAuth(),
                    transactionId = randomTransaction()
                )

        }

    }

    fun reset() {

        _paymentState.value =
            PaymentState.PaymentMethod

    }

    private fun randomAuth(): String {

        val chars =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

        return (1..6)
            .map { chars.random() }
            .joinToString("")
    }

    private fun randomTransaction(): String {

        return (1..12)
            .map {
                Random.nextInt(10)
            }
            .joinToString("")
    }

}