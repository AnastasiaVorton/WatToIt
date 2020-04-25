package com.example.wattoit.data

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService {

    @POST("users/login/")
    fun login(@Body credentials: RequestBody): Call<LoginResponse>
}
