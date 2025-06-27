package com.dark.product_app.repo

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.net.URL

object ProductRepository {

    suspend fun fetchProductData(context: Context): ProductResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val jsonString = getJsonFile(context)
                Json { ignoreUnknownKeys = true }.decodeFromString<ProductResponse>(jsonString)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun loadImageSafe(url: String, context: Context, name: String): ImageBitmap {
        return withContext(Dispatchers.IO) {
            try {
                val safeName = name.replace(Regex("[^A-Za-z0-9_.-]"), "_")
                val bitMapFile = File(context.filesDir, "$safeName.jpg")

                val bitmap = if (bitMapFile.exists()) {
                    BitmapFactory.decodeStream(bitMapFile.inputStream())
                } else {
                    val inputStream = URL(url).openStream()
                    bitMapFile.writeBytes(inputStream.readBytes())
                    BitmapFactory.decodeStream(bitMapFile.inputStream())
                }

                bitmap?.asImageBitmap() ?: ImageBitmap(1, 1)
            } catch (e: Exception) {
                e.printStackTrace()
                ImageBitmap(1, 1)
            }
        }
    }

    private fun getJsonFile(context: Context): String {
        val file = File(context.filesDir, "product.json")

        return try {
            if (file.exists()) {
                file.readText()
            } else {
                val url = URL("https://drive.google.com/uc?export=download&id=1FAeT8xVvnM9UkAx-7d1X4gf2Z_1IPJgJ")
                val content = url.readText()
                file.writeText(content)
                content
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}
