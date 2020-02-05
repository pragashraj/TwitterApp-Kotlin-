package com.example.twitter

import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @POST("signUp")
    fun Regeister(@Body newUser: NewUser):Call<NewUser>

    @Multipart
    @POST("postImage")
    fun sendImage(@Part filePart: MultipartBody.Part, @Part("name") name: String):Call<Response>


    @GET("getDetails")
    fun getMyDetails():Call<NewUser>


    @FormUrlEncoded
    @POST("postTweet")
    fun saveTweet(
        @Field("username") username:String,
        @Field("imagepath") imagepath:String,
        @Field("usertweet") usertweet:String,
        @Field("attachmentpath") attachmentpath:String
    ):Call<Response>


    @POST("posttweet")
    fun sendTweet(@Body tweet: Tweet):Call<Tweet>

}