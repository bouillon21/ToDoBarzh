package com.example.todobarzh.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object RetrofitBuilder {

    private val httpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.addHeader("Authorization", NetworkPreference.TOKEN)
            chain.proceed(requestBuilder.build())
        }.build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl(NetworkPreference.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val networkService: TodoListService = retrofit.create(TodoListService::class.java)
}