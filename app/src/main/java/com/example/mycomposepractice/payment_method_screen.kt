package com.example.mycomposepractice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethodScreen(
    navController: NavHostController,
    amount: String,
    onCardClicked: () -> Unit
) {

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
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
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
    ){padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {

                Text(
                    text = amount,
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "đ",
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

            }

            Spacer(modifier = Modifier.height(32.dp))

            PaymentOptionCard(
                title = "Cash",
                enabled = false,
                selected = false,
                icon = {
                    Icon(
                        Icons.Outlined.ShoppingCart,
                        null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    )
                }
            ) {}

            Spacer(modifier = Modifier.height(18.dp))

            PaymentOptionCard(
                title = "Card",
                enabled = true,
                selected = true,
                icon = {
                    Icon(
                        Icons.Outlined.AccountCircle,
                        null,
                        tint = Color(0xFF0F6E7A)
                    )
                }
            ) {
                onCardClicked()
            }

            Spacer(modifier = Modifier.height(18.dp))

            PaymentOptionCard(
                title = "QR",
                enabled = false,
                selected = false,
                icon = {
                    Icon(
                        Icons.Outlined.Search,
                        null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    )
                }
            ) {}

        }
    }

}

@Composable
fun Scaffold(content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}