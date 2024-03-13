package com.example.productstoreretrofit.data

import com.example.productstoreretrofit.data.model.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

    @GET("products")
    suspend fun getProduct(): Product

    @GET("products/{type}")
    suspend fun getTestProduct(
        @Path("type") type: String,
        @Query("page") page:Int
    ): Product

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
}