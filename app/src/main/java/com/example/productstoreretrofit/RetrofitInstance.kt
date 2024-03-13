package com.example.productstoreretrofit

import com.example.productstoreretrofit.data.API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    private val intIterator:HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client:OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(intIterator)
        .build()
    val api:API = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(API.BASE_URL)
        .client(client)
        .build()
        .create(API::class.java)

}