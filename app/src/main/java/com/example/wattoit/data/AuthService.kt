package com.example.wattoit.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PictureService {

    @POST("users/login")
    fun login(
        @Query("username") usernameValue: String,
        @Query("password") passwordValue: String
    ): Call<LoginResponse>
}
