package com.dark.product_app.components.button

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.dark.product_app.R

@Composable
fun ViewAll(color: Color = MaterialTheme.colorScheme.primary) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("View All", color = color, fontWeight = FontWeight.Bold)
        Icon(
            painterResource(R.drawable.chevron_right),
            tint = color,
            contentDescription = "View All"
        )
    }
}