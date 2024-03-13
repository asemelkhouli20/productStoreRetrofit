package com.example.productstoreretrofit.presntation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productstoreretrofit.data.ProductRepository
import com.example.productstoreretrofit.data.Result
import com.example.productstoreretrofit.data.model.ProductX
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: ProductRepository
): ViewModel() {
    private val _product = MutableStateFlow<List<ProductX>>(emptyList())
    val product = _product.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val  showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            productRepository.getProductList().collectLatest {
               when(it){
                   is Result.Error -> {
                      _showErrorToastChannel.send(true)
                   }
                   is Result.Success -> {
                       it.data?.let {product->
                           _product.update { product }
                       }
                   }
               }
            }
        }
    }
}











