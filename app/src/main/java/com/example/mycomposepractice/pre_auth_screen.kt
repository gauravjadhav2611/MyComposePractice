package com.example.mycomposepractice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mycomposepractice.ui.PreAuthFlowScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreAuthScreen(navController: NavHostController) {

    var amount by remember {
        mutableStateOf("")
    }

    val keys = listOf(
        "C",
        "⌫",
        "Million",
        "7",
        "8",
        "9",
        "×",
        "4",
        "5",
        "6",
        "+",
        "1",
        "2",
        "3",
        "-",
        "000",
        "0",
        "="
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF7E8D6)
                        )
                    ) {
                        Text(
                            "PRE-AUTH",
                            modifier = Modifier.padding(horizontal = 40.dp, vertical = 6.dp),
                            color = Color(0xFFC57C00),
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        },

        bottomBar = {

            Button(
                onClick = {
                    navController.navigate("selectPayment/$amount")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .height(70.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2F9E44)
                ),
                shape = RoundedCornerShape(0.dp)
            ) {

                Text(
                    text = "CONFIRM PRE-AUTH ${if (amount.isEmpty()) "0" else amount} đ",
                    fontSize = 24.sp
                )
            }

        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAF2F6))
                .padding(padding)
                .padding(16.dp)
        ) {

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ){
                Text(
                    text = if (amount.isEmpty()) "0" else amount,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Light,
                    color = Color(0xFFC57C00)
                )
                Text(
                    text = "đ",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Light,
                    color = Color(0xFF626262)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {

                items(keys,
                    span = { key ->
                        GridItemSpan(if(key == "Million" || key == "=") 2 else 1)
                    }
                ) { key ->

                    CalculatorButton(
                        text = key,
                        onClick = {

                            when (key) {

                                "C" -> amount = ""

                                "⌫" -> {
                                    if (amount.isNotEmpty()) {
                                        amount = amount.dropLast(1)
                                    }
                                }

                                "Million" -> {

                                    val value = amount.toLongOrNull() ?: 0

                                    amount = (value * 1_000_000).toString()

                                }

                                "=" -> {

                                }

                                "+", "-", "×" -> {

                                }

                                else -> {
                                    amount += key
                                }
                            }

                        }
                    )

                }

            }

        }

    }

}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit
) {

    val background = when (text) {

        "C" -> Color(0xFFF9DADA)

        "=" -> Color(0xFFD8F2F5)

        else -> Color.White
    }

    Button(
        onClick = onClick,
        modifier = Modifier
            .height(64.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = background
        ),
        contentPadding = PaddingValues(0.dp)
    ) {

        if (text == "⌫") {

            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = null,
                tint = Color.DarkGray
            )

        } else {

            Text(
                text = text,
                fontSize = if (text == "Million") 18.sp else 28.sp,
                color = if (text == "C")
                    Color.Red
                else
                    Color.Black
            )

        }

    }

}