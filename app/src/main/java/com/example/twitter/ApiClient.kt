package com.example.twitter

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private final var BASE_URL="http://10.0.2.2:8080/Api/"
    lateinit var retrofit: Retrofit

    fun getApiClient(): Retrofit {
        if(retrofit==null)
        {
            retrofit=Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}