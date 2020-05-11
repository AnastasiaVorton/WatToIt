package com.example.wattoit.login.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success")
    val successCode: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("token")
    val token: String
)

data class RegistrationResponse(
    @SerializedName("success")
    val successCode: Int
)