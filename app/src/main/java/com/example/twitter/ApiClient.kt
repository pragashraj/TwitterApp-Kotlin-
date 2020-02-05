package com.example.twitter

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private final var BASE_URL="http://192.168.8.105:8080/api/"
    lateinit var retrofit: Retrofit

    val instance: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }
}