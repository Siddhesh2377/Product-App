package com.dark.product_app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dark.product_app.R
import com.dark.product_app.data.ProductLabel
import com.dark.product_app.repo.Product
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductInfo(product: Product) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // Product Details Title
        Text(
            "Product Details",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            product.specifications.forEach { (key, value) ->
                val description = (value as? JsonPrimitive)?.contentOrNull ?: ""
                InfoRow(Modifier.fillMaxWidth(), label = key, description = description)
            }
        }
        // Info rows

        Spacer(Modifier.height(8.dp))

        // Key Features Title
        Text(
            "Key Features",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        // Feature tags (Wrap Row for responsiveness)
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            val modifier = Modifier.weight(1f)

            val features = product.features
            val totalRows = (features.size + 1) / 2  // Handles odd size safely

            repeat(totalRows) { rowIndex ->
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val firstItem = features[rowIndex * 2]
                    InfoRow(modifier, label = firstItem)

                    // Handle potential missing second item for odd-sized lists
                    if (rowIndex * 2 + 1 < features.size) {
                        val secondItem = features[rowIndex * 2 + 1]
                        InfoRow(modifier, label = secondItem)
                    } else {
                        Spacer(modifier)
                    }
                }
            }
        }
    }
}


@Composable
fun InfoRow(modifier: Modifier = Modifier, label: String, description: String = "") {

    val icon = ProductLabel.fromDisplayName(label)?.icon ?: R.drawable.material

    Row(
        modifier = modifier
            .background(Color(0xFFF2F2F2), RoundedCornerShape(6.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Info",
            tint = Color.Gray,
            modifier = Modifier.size(18.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text("$label: ", fontWeight = FontWeight.Medium, maxLines = 1)
        if (description.isNotEmpty())
            Text(description, color = Color.DarkGray)
    }
}