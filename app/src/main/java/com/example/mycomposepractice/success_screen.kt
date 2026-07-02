package com.example.mycomposepractice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SuccessScreen(
    amount: String,
    authCode: String,
    transactionId: String,
    onYes: () -> Unit,
    onNo: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF3F7))
            .navigationBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color(0xFF2E9E44),
            modifier = Modifier.size(110.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Pre-Authorization Successful",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Amount Held  $amount")

        Spacer(modifier = Modifier.height(24.dp))

        Text("Auth Code  $authCode")

        Spacer(modifier = Modifier.height(8.dp))

        Text("Transaction#  $transactionId")

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            OutlinedButton(
                onClick = onNo,
                modifier = Modifier.weight(1f)
            ) {
                Text("NO")
            }

            Button(
                onClick = onYes,
                modifier = Modifier.weight(1f)
            ) {
                Text("YES")
            }

        }

    }

}