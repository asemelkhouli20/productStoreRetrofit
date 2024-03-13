package com.example.productstoreretrofit.data

import com.example.productstoreretrofit.data.model.Product
import com.example.productstoreretrofit.data.model.ProductX
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProductList():Flow<Result<List<ProductX>>>
}