package com.example.twitter

import retrofit2.http.POST
import retrofit2.Call;
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface Api {
    
    @POST("register")
    fun signUpInfo(@Body newUser: NewUser):Call<Response>

    @FormUrlEncoded
    @POST("register")
    fun signUp(
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("username") username:String
    ):Call<Response>
}