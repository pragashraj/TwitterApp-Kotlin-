package com.example.twitter

import okhttp3.MultipartBody
import retrofit2.Call;
import retrofit2.http.*

interface Api {
    @FormUrlEncoded
    @POST("register")
    fun signUp(
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("username") username:String
    ):Call<Response>


    @Multipart
    @POST("postImage")
    fun sendImage(@Part filePart: MultipartBody.Part):Call<Response>
}