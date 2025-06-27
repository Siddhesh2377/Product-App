package com.dark.product_app.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dark.product_app.R
import com.dark.product_app.ui.theme.DarkPrimary
import com.dark.product_app.ui.theme.LightPrimary
import kotlinx.coroutines.delay

@SuppressLint("DefaultLocale")
@Composable
fun LimitedTimeOfferCard() {
    var remainingTime by remember { mutableIntStateOf(23 * 3600 + 56 * 60 + 59) }

    // Countdown logic
    LaunchedEffect(Unit) {
        while (remainingTime > 0) {
            delay(1000)
            remainingTime--
        }
    }

    // Convert seconds to HH:MM:SS format
    val hours = remainingTime / 3600
    val minutes = (remainingTime % 3600) / 60
    val seconds = remainingTime % 60
    val timeDisplay = String.format("%02d:%02d:%02d", hours, minutes, seconds)

    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            LightPrimary,
            DarkPrimary
        )
    )

    Card(
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .fillMaxWidth()
            .background(gradientBrush, RoundedCornerShape(8.dp))
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Spacer(Modifier.width(0.dp))
                Icon(
                    painter = painterResource(R.drawable.zap),
                    contentDescription = "Offer",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Limited Time Offer",
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            Spacer(Modifier.weight(1f))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Ends in:",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                )
                Spacer(Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = timeDisplay,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = DarkPrimary
                    )
                }
                Spacer(Modifier.width(6.dp))
            }
        }
    }
}
