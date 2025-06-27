package com.dark.product_app.repo

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ProductResponse(
    val product: Product = Product()
)
@Serializable
data class Product(
    val id: String = "",
    val name: String = "",
    val brand: String = "",
    val category: String = "",
    val price: Price = Price(0.0, 0.0, "", ""),
    val images: List<String> = emptyList(),
    val thumbnails: List<String> = emptyList(),
    val description: String = "",
    val specifications: JsonObject = JsonObject(emptyMap()), //= Specifications("", "", "", "", "", "", ""),
    val features: List<String> = emptyList(),
    val variants: Variants = Variants(emptyList()),
    val stock: Stock = Stock("", 0, 0),
    val rating: Rating = Rating(0.0, 0, Distribution(0, 0, 0, 0, 0)),
    val metadata: Metadata = Metadata("", "", "", "", "", ""),
    val seo: Seo = Seo("", "", emptyList()),
    val availability: Availability = Availability("", "", emptyList())
)
@Serializable
data class Price(
    val current: Double,
    val original: Double,
    val currency: String,
    val currencySymbol: String
)
@Serializable
data class Specifications(
    val material: String,
    val gender: String,
    val condition: String,
    val type: String,
    val packaging: String,
    val quantity: String,
    val size: String
)
@Serializable
data class Variants(
    val sizes: List<String>
)
@Serializable
data class Stock(
    val status: String,
    val quantity: Int,
    val lowStockThreshold: Int
)
@Serializable
data class Rating(
    val average: Double,
    val count: Int,
    val distribution: Distribution
)
@Serializable
data class Distribution(
    val `5`: Int,
    val `4`: Int,
    val `3`: Int,
    val `2`: Int,
    val `1`: Int
)
@Serializable
data class Metadata(
    val productCode: String,
    val sku: String,
    val weight: String,
    val dimensions: String,
    val manufacturer: String,
    val countryOfOrigin: String
)
@Serializable
data class Seo(
    val title: String,
    val description: String,
    val keywords: List<String>
)
@Serializable
data class Availability(
    val status: String,
    val estimatedDelivery: String,
    val regions: List<String>
)
