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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.dark.product_app.components.Chip
import com.dark.product_app.components.GradientButton
import com.dark.product_app.components.InfoStackText
import com.dark.product_app.components.StockInfo
import com.dark.product_app.repo.Product
import com.dark.product_app.repo.ProductResponse
import com.dark.product_app.repo.fetchProductData
import com.dark.product_app.repo.loadImageSafe
import com.dark.product_app.ui.theme.ProductAppTheme
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
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
                        .fillMaxSize(),
                    bottomBar = {
                        BottomBar()
                    }
                ) { innerPadding ->
                    HomeScreen(innerPadding)
                }
            }
        }
    }
}

@Composable
fun BottomBar() {
    BottomAppBar(containerColor = MaterialTheme.colorScheme.background) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier.size(50.dp),
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Icon(
                    painterResource(R.drawable.heart_empty),
                    contentDescription = null
                )
            }

            GradientButton(
                Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {

            }
            IconButton(
                onClick = {},
                modifier = Modifier.size(50.dp),
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Icon(
                    Icons.Outlined.Share,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun HomeScreen(paddingValues: PaddingValues) {
    Column(modifier = Modifier.padding(paddingValues)) {
        var data by remember { mutableStateOf(ProductResponse()) }
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            val productData = fetchProductData(context)
            data = productData
        }

        TopBar()
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top
        ) {
            ProductImages(data)
            ProductDetail(data.product)
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

@Composable
fun ProductImages(data: ProductResponse) {
    val context = LocalContext.current
    var otherProductImages by remember { mutableStateOf(listOf<ImageBitmap?>()) }
    var currentProductImage by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(data) {
        val imagesList = data.product.images
        // Fetch current image separately
        if (imagesList.isNotEmpty())
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
        if (currentProductImage != null) {
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
                    OutlinedCard(modifier = Modifier.clickable {
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
fun ProductDetail(product: Product) {
    Column(
        Modifier.padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Chip(product.category)
            Spacer(Modifier.width(10.dp))
            Chip(product.brand)
            Spacer(Modifier.weight(1f))
            StockInfo("In Stock \n${product.stock.quantity} Units")
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                product.name,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    product.price.currencySymbol + product.price.current.toInt().toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    product.price.currencySymbol + product.price.original.toInt().toString(),
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = TextDecoration.LineThrough
                )
                Text(
                    "Save ${product.price.currencySymbol}${(product.price.original - product.price.current).toInt()}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxWidth()
            ) {
                InfoStackText(
                    modifier = Modifier.weight(1f),
                    title = "Product Code:",
                    value = product.metadata.productCode
                )

                InfoStackText(
                    modifier = Modifier.weight(1f),
                    title = "SKU: ",
                    value = product.metadata.sku
                )
            }
        }

        Row {
            val rating = product.rating.average

            Spacer(Modifier.width(15.dp))

            RatingBar(
                value = rating.toFloat(),
                style = RatingBarStyle.Stroke(),
                spaceBetween = 0.dp,
                size = 20.dp,
                onValueChange = {},
                onRatingChanged = {}
            )

            Spacer(Modifier.width(5.dp))

            Text(product.rating.average.toString(), fontWeight = FontWeight.Bold)


            Spacer(Modifier.width(15.dp))

            Text("(${product.rating.count} Ratings)")

            Spacer(Modifier.weight(1f))


            Text("View All")
            Icon()
        }
    }
}