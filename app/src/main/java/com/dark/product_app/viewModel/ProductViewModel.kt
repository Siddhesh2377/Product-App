package com.dark.product_app.viewModel

import android.app.Application
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dark.product_app.repo.ProductRepository
import com.dark.product_app.repo.ProductResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val _productData = MutableStateFlow<ProductResponse?>(null)
    val productData: StateFlow<ProductResponse?> = _productData

    private val _imageMap = MutableStateFlow<Map<String, ImageBitmap>>(emptyMap())
    val imageMap: StateFlow<Map<String, ImageBitmap>> = _imageMap

    fun loadProductData() {
        viewModelScope.launch {
            val data = ProductRepository.fetchProductData(getApplication())
            _productData.value = data
        }
    }

    fun loadImage(url: String, name: String) {
        viewModelScope.launch {
            val image = ProductRepository.loadImageSafe(url, getApplication(), name)
            _imageMap.value = _imageMap.value + (name to image)
        }
    }
}
