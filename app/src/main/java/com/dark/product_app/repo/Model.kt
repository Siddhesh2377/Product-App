package com.dark.product_app.repo

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val product: Product
)
@Serializable
data class Product(
    val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val price: Price,
    val images: List<String>,
    val thumbnails: List<String>,
    val description: String,
    val specifications: Specifications,
    val features: List<String>,
    val variants: Variants,
    val stock: Stock,
    val rating: Rating,
    val metadata: Metadata,
    val seo: Seo,
    val availability: Availability
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
