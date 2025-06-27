package com.dark.product_app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dark.product_app.ui.theme.DarkPrimary
import com.dark.product_app.ui.theme.LightPrimary

@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // define your gradient brush
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            LightPrimary,
            DarkPrimary
        )
    )

    Button(
        onClick = onClick,
        // make the default background transparent
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),
        contentPadding = ButtonDefaults.ContentPadding,
        modifier = modifier
            .background(                          // draw the gradient
                brush = gradientBrush,
                shape = RoundedCornerShape(24.dp)  // match your button’s shape
            )
            .clip(RoundedCornerShape(24.dp))     // ensure ripple & click area are clipped
    ) {
        Icon(Icons.Outlined.ShoppingCart, contentDescription = null)
        Spacer(Modifier.width(10.dp))
        Text(
            "Add to Cart",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}
