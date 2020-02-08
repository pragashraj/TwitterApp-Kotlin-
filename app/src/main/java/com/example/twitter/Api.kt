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

    @GET("getPicture")
    fun getProfilePic(@Query("name")username: String):Call<Response>

    @GET("getDetails")
    fun getMyDetails():Call<NewUser>

    @POST("posttweet")
    fun sendTweet(@Body tweet: Tweet):Call<Void>

}