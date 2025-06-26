package com.dark.product_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dark.product_app.repo.ProductResponse
import com.dark.product_app.repo.fetchProductData
import com.dark.product_app.repo.loadImageSafe
import com.dark.product_app.ui.theme.ProductAppTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductAppTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    HomeScreen(innerPadding)
                }
            }
        }
    }
}
@Composable
fun HomeScreen(paddingValues: PaddingValues) {
    Column(modifier = Modifier.padding(paddingValues)) {
        TopBar()
        ProductImages()
    }
}
@Composable
fun ProductImages() {
    val context = LocalContext.current

    var data by remember { mutableStateOf<ProductResponse?>(null) }
    var otherProductImages by remember { mutableStateOf(listOf<ImageBitmap?>()) }
    var currentProductImage by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(Unit) {
        val productData = fetchProductData(context)
        data = productData
        val imagesList = productData.product.images

        // Fetch current image separately
        currentProductImage = loadImageSafe(imagesList[0], context, "product_image_0")

        // Parallel fetch of all images including first if needed
        otherProductImages = imagesList.mapIndexed { index, imageUrl ->
            async { loadImageSafe(imageUrl, context, "product_image_$index") }
        }.awaitAll()
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (data != null && currentProductImage != null) {
            Crossfade(currentProductImage) {
                Image(
                    bitmap = it!!,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
        } else {
            Text("Loading...")
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(otherProductImages) { otherImage ->
                if (otherImage != null) {
                    OutlinedCard(modifier = Modifier.clickable{
                        currentProductImage = otherImage
                    }, border = BorderStroke(1.dp, Color.LightGray)) {
                        Image(
                            bitmap = otherImage,
                            contentDescription = null,
                            modifier = Modifier
                                .size(120.dp)
                                .padding(8.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconSize = 34.dp

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            modifier = Modifier.size(iconSize),
            contentDescription = null
        )
        Text(
            text = "Product App",
            modifier = Modifier.padding(vertical = 12.dp),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
        Icon(
            imageVector = Icons.TwoTone.Search,
            modifier = Modifier.size(iconSize),
            contentDescription = null
        )
    }
}