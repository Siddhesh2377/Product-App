package com.dark.product_app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dark.product_app.ui.theme.DarkPrimary
import com.dark.product_app.ui.theme.LightPrimary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    onClick: (STATE) -> Unit
) {
    var state by remember { mutableStateOf(STATE.ADD_TO_CART) }


    // define your gradient brush
    val gradientBrush = if (state == STATE.ADD_TO_CART) Brush.horizontalGradient(
        colors = listOf(
            LightPrimary,
            DarkPrimary
        )
    ) else
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF00C47C),
                Color(0xFF00C47C)
            )
        )

    val icon =
        if (state == STATE.ADD_TO_CART) Icons.Outlined.ShoppingCart else Icons.Outlined.CheckCircle



    Button(
        onClick = {
            onClick(state)
            if (state == STATE.ADD_TO_CART) {
                CoroutineScope(Dispatchers.Main).launch {
                    state = STATE.ADDED_TO_CART
                    delay(2500)
                    state = STATE.ADD_TO_CART
                }
            }
        },
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
        Icon(icon, contentDescription = null)
        Spacer(Modifier.width(10.dp))
        Text(
            if (state == STATE.ADD_TO_CART) "Add to Cart" else "Added to Cart",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

enum class STATE {
    ADD_TO_CART,
    ADDED_TO_CART
}
