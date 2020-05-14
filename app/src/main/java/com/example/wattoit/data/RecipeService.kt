package com.example.wattoit.data

import com.example.wattoit.login.data.LoginResponse
import com.example.wattoit.login.data.RegistrationResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RecipeService {
    @Headers("Content-Type: application/json")
    @POST("users/login/")
    fun login(@Body credentials: RequestBody): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("users/")
    fun register(@Body credentials: RequestBody): Call<RegistrationResponse>
}
