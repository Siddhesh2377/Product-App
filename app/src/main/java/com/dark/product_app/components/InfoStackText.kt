package com.dark.product_app.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun InfoStackText(modifier: Modifier = Modifier, title: String, value: String) {
    Column(modifier = modifier) {
        Text(title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal))
        Text(value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
    }
}