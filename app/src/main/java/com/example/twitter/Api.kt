package com.example.twitter

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call;
import retrofit2.http.*

interface Api {
    @POST("register")
    fun Regeister(@Body newUser: NewUser):Call<Void>

    @Multipart
    @POST("postImage")
    fun sendImage(@Part filePart: MultipartBody.Part, @Part("name") name: String):Call<Void>

    @GET("getTweet")
    fun getAllTweet():Call<List<Tweet>>

    @POST("posttweet")
    fun sendTweet(@Body tweet: Tweet):Call<Void>

}