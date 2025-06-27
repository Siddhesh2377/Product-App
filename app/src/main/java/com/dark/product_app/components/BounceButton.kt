package com.dark.product_app.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp

@Composable
fun BounceButton(text: String, buttonColor: Color, onClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .bounceClick()
            .height(40.dp),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = buttonColor.copy(alpha = 0.1f),
            contentColor = buttonColor,
        ),
        border = BorderStroke(1.dp, buttonColor)
    ) {
        Text(text)
    }
}