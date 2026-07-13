package com.example.mycomposepractice.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PaymentOptionCard(
    title: String,
    icon: @Composable () -> Unit,
    enabled: Boolean = true,
    selected: Boolean = false,
    onClick: () -> Unit
) {

    val borderColor =
        if (selected)
            Color(0xFF0F6E7A)
        else
            Color.Transparent

    val textColor =
        if (enabled)
            MaterialTheme.colorScheme.onSurface
        else
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable(enabled = enabled) {
                onClick()
            },
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            if (selected) 4.dp else 1.dp,
            borderColor
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            icon()

            Spacer(modifier = Modifier.width(18.dp))

            Text(
                text = title,
                fontSize = 22.sp,
                color = textColor,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.weight(1f))

            if (!enabled) {

                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                )

            }

        }

    }

}